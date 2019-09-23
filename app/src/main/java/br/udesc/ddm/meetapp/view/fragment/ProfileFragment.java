package br.udesc.ddm.meetapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.view.activity.SigninActivity;


public class ProfileFragment extends Fragment {

    Button button;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        button = view.findViewById(R.id.exitAppProfile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SigninActivity.class));
            }
        });

        return view;
    }

}
