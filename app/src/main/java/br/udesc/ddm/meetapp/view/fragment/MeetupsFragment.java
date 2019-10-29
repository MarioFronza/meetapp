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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import br.udesc.ddm.meetapp.view.adapter.MeetupAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MeetupsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private SharedPreferences preferences;
    private Button buttonDate;
    private List<Meetup> meetups;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetups, container, false);

        buttonDate = view.findViewById(R.id.buttonDate);

        meetups = new ArrayList<>();
        requestsAllMeetups();
        setCurrentDate();

        recyclerView = view.findViewById(R.id.recyclerMeetups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MeetupAdapter(meetups));
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        return view;
    }

    private void requestsAllMeetups() {
        preferences = this.getActivity().getSharedPreferences("meetappPreferences", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        Call<ResponseBody> call = new RetrofitInitializer().getMeetupService().getMeetups("Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
//                    List<Meetup> meetupsList = (List<Meetup>) response.body();
                } else {
                    JSONObject jObjError;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        buttonDate.setText(date);
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
