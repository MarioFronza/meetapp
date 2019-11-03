package br.udesc.ddm.meetapp.view.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import br.udesc.ddm.meetapp.util.DownloadImageTask;
import br.udesc.ddm.meetapp.view.activity.DetailsActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MeetupAdapter extends RecyclerView.Adapter<MeetupAdapter.MyViewHolder> {

    private List<Meetup> meetups;

    public MeetupAdapter(List<Meetup> meetups) {
        this.meetups = meetups;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView location;
        private TextView date;
        private TextView user;
        private ImageView image;
        private Button button;
        public View view;
        private Meetup currentMeetup;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            location = itemView.findViewById(R.id.textViewLocation);
            date = itemView.findViewById(R.id.textViewDate);
            user = itemView.findViewById(R.id.textViewUser);
            image = itemView.findViewById(R.id.imageMeetup);
            button = itemView.findViewById(R.id.btnMeetupItem);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    intent.putExtra("meetup", currentMeetup);
                    v.getContext().startActivity(intent);
                }
            });

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetup_item, parent, false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.currentMeetup = meetups.get(position);
        Meetup meetup = meetups.get(position);
        holder.title.setText(meetup.getTitle());
        holder.location.setText(meetup.getLocation());
        holder.date.setText(formatDate(meetup.getDate()));
        holder.user.setText(meetup.getUser().getName());
        new DownloadImageTask(holder.image).execute(meetups.get(position).getImage().getUrl());
        holder.button.setText(R.string.text_meetup_registration);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SharedPreferences preferences = v.getContext().getSharedPreferences("meetappPreferences", MODE_PRIVATE);
                String token = preferences.getString("token", "");
                int id = meetups.get(position).getId();
                Call<ResponseBody> call = new RetrofitInitializer().getSubscriptionsService().createSubscriptions("Bearer " + token, id);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(v.getContext(), "Inscrição realizada com sucesso", Toast.LENGTH_LONG).show();
                        } else {
                            JSONObject jObjError;
                            try {
                                jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(v.getContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                            } catch (JSONException | IOException e) {
                                Toast.makeText(v.getContext(), "Erro na requisição", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(v.getContext(), "Erro na requisição", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date d = sd.parse(dateString);
            sd = new SimpleDateFormat("dd/MM/yyyy");
            return sd.format(d);
        } catch (ParseException e) {
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return meetups.size();
    }


}
