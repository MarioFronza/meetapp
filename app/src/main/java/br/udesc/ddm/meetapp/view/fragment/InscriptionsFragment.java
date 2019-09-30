package br.udesc.ddm.meetapp.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.view.adapter.InscriptionAdapter;
import br.udesc.ddm.meetapp.view.adapter.MeetupAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class InscriptionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Meetup> inscriptions;


    public InscriptionsFragment() {
        this.inscriptions = new ArrayList<>();
        setInscriptionsList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscriptions, container, false);

        recyclerView = view.findViewById(R.id.recyclerInscriptions);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new InscriptionAdapter(inscriptions));


        return view;
    }

    private void setInscriptionsList() {
        Meetup meetup = new Meetup("Meetup React Native", "Rua Dr. Getúlio Vargas, 2000", "02 de Setembro, às 20h", "Organizador: João Pedro Schmitz", R.drawable.meetup);
        this.inscriptions.add(meetup);
    }

}
