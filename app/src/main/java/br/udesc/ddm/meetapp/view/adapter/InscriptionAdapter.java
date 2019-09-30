package br.udesc.ddm.meetapp.view.adapter;

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

public class InscriptionAdapter extends RecyclerView.Adapter<InscriptionAdapter.MyViewHolder> {

    private List<Meetup> inscriptions;

    public InscriptionAdapter(List<Meetup> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView location;
        private TextView date;
        private TextView user;
        private ImageView image;
        private Button button;
        private View view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            location = itemView.findViewById(R.id.textViewLocation);
            date = itemView.findViewById(R.id.textViewDate);
            user = itemView.findViewById(R.id.textViewUser);
            image = itemView.findViewById(R.id.imageMeetup);
            button = itemView.findViewById(R.id.btnMeetupItem);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetup_item, parent, false);

        return new InscriptionAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meetup meetup = inscriptions.get(position);
        holder.title.setText(meetup.getTitle());
        holder.location.setText(meetup.getLocation());
        holder.date.setText(meetup.getDate());
        holder.user.setText(meetup.getUser());
        holder.image.setImageResource(meetup.getImage());
        holder.button.setText(R.string.text_cancel_meetup_registration);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
