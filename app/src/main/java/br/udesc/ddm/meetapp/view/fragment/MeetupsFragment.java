package br.udesc.ddm.meetapp.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.view.adapter.MeetupAdapter;


public class MeetupsFragment extends Fragment {

    private RecyclerView recyclerView;

    public MeetupsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetups, container, false);

        recyclerView = view.findViewById(R.id.recyclerMeetups);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MeetupAdapter());


        return view;
    }


}
