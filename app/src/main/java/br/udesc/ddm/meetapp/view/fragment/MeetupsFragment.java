package br.udesc.ddm.meetapp.view.fragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import br.udesc.ddm.meetapp.view.adapter.MeetupAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MeetupsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private FrameLayout progressBarHolder;
    private SharedPreferences preferences;
    private Button buttonDate;
    private List<Meetup> meetups;
    private String queryDate;


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetups, container, false);

        recyclerView = view.findViewById(R.id.recyclerMeetups);
        progressBarHolder = view.findViewById(R.id.progressBarHolder);
        buttonDate = view.findViewById(R.id.buttonDate);


        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        requestsAllMeetups(false);
        setCurrentDate();

        return view;
    }

    private void requestsAllMeetups(boolean withDate) {
        showProgressBar(true);
        preferences = this.getActivity().getSharedPreferences("meetappPreferences", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        Call<List<Meetup>> call;
        if (withDate) {
            Toast.makeText(getActivity(), queryDate, Toast.LENGTH_LONG).show();
            call = new RetrofitInitializer().getMeetupService().getMeetupsWithDate("Bearer " + token, queryDate);
        } else {
            call = new RetrofitInitializer().getMeetupService().getMeetups("Bearer " + token);
        }

        call.enqueue(new Callback<List<Meetup>>() {
            @Override
            public void onResponse(Call<List<Meetup>> call, Response<List<Meetup>> response) {
                if (response.isSuccessful()) {
                    meetups = response.body();
                    setRecyclerViewData();
                    showProgressBar(false);
                } else {
                    try {
                        toastErrorMessage(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Meetup>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
            }
        });
        showProgressBar(false);
    }

    private void toastErrorMessage(String error) {
        JSONObject jObjError;
        try {
            jObjError = new JSONObject(error);
            Toast.makeText(getActivity(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBarHolder.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            buttonDate.setVisibility(View.GONE);
        } else {
            progressBarHolder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            buttonDate.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MeetupAdapter(meetups));
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        String strDate = dayOfMonth + "-" + (month + 1) + "-" + year;

        DateFormat formatter;

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        
        try {
            Date newDate = formatter.parse(strDate);
            queryDate = Long.toString(newDate.getTime());
            buttonDate.setText(date);
            requestsAllMeetups(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentDate() {
        String date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        buttonDate.setText("Selecionar data");
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

}
