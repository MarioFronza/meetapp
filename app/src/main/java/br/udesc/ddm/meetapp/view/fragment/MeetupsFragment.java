package br.udesc.ddm.meetapp.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.view.adapter.MeetupAdapter;


public class MeetupsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private Button buttonDate;
    private List<Meetup> meetups;

    public MeetupsFragment() {
        this.meetups = new ArrayList<>();
        setMeetupsList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetups, container, false);

        buttonDate = view.findViewById(R.id.buttonDate);
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


    private void setMeetupsList() {
        Meetup meetup = new Meetup("Meetup React Native", "Rua Dr. Getúlio Vargas, 2000", "02 de Setembro, às 20h", "Organizador: João Pedro Schmitz", R.drawable.meetup);
        this.meetups.add(meetup);
        meetup = new Meetup("Meetup Node.js", "Rua Dr. Getúlio Vargas, 2000", "02 de Setembro, às 20h", "Organizador: Mário Fronza", R.drawable.meetupnode);
        this.meetups.add(meetup);
        meetup = new Meetup("Meetup Android", "Rua Dr. Getúlio Vargas, 2000", "02 de Setembro, às 20h", "Organizador: Igor Martins", R.drawable.meetupandroid);
        this.meetups.add(meetup);
    }
}
