package br.udesc.ddm.meetapp.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.udesc.ddm.meetapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMeetupFragment extends Fragment {


    public NewMeetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_meetup, container, false);
    }

}
