package br.udesc.ddm.meetapp.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.view.activity.DetailsActivity;

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
        public Meetup currentMeetup;

        public MyViewHolder(@NonNull View itemView) {
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
                    v.getContext().startActivity(new Intent(v.getContext(), DetailsActivity.class));
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.currentMeetup = meetups.get(position);
        Meetup meetup = meetups.get(position);
        holder.title.setText(meetup.getTitle());
        holder.location.setText(meetup.getLocation());
        holder.date.setText(meetup.getDate());
        holder.user.setText("Mario");
        holder.button.setText(R.string.text_meetup_registration);

    }

    @Override
    public int getItemCount() {
        return meetups.size();
    }


}
