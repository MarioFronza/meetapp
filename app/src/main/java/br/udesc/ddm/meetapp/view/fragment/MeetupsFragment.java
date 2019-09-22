package br.udesc.ddm.meetapp.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.udesc.ddm.meetapp.R;


public class MeetupsFragment extends Fragment {

    public MeetupsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meetups, container, false);
    }


}
