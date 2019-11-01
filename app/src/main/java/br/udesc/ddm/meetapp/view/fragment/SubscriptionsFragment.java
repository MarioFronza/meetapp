package br.udesc.ddm.meetapp.view.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import br.udesc.ddm.meetapp.view.adapter.InscriptionAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionsFragment extends Fragment {

    private SharedPreferences preferences;
    private FrameLayout progressBarHolder;
    private TextView subscriptionTextView;
    private RecyclerView recyclerView;
    private List<Meetup> inscriptions;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);

        progressBarHolder = view.findViewById(R.id.subcriptionProgressBarHolder);
        recyclerView = view.findViewById(R.id.recyclerInscriptions);
        subscriptionTextView = view.findViewById(R.id.subscriptionTextView);
        showProgressBar(true);

        requestAllInscriptions();
        return view;
    }

    private void requestAllInscriptions() {
        preferences = this.getActivity().getSharedPreferences("meetappPreferences", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        Call<List<Meetup>> call = new RetrofitInitializer().getSubscriptionsService().getSubscriptions("Bearer " + token);

        call.enqueue(new Callback<List<Meetup>>() {
            @Override
            public void onResponse(Call<List<Meetup>> call, Response<List<Meetup>> response) {
                if (response.isSuccessful()) {
                    inscriptions = response.body();
                    setRecyclerViewData();
                    showProgressBar(false);
                } else {
                    try {
                        toastErrorMessage(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    showProgressBar(false);
                }
            }

            @Override
            public void onFailure(Call<List<Meetup>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
                showProgressBar(false);
            }
        });
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
        } else {
            progressBarHolder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new InscriptionAdapter(inscriptions));
        if(inscriptions.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            subscriptionTextView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            subscriptionTextView.setVisibility(View.GONE);
        }
    }


}
