package br.udesc.ddm.meetapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.udesc.ddm.meetapp.R;

public class MeetupAdapter extends RecyclerView.Adapter<MeetupAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView location;
        private TextView date;
        private TextView user;
        private ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            location = itemView.findViewById(R.id.textViewLocation);
            date = itemView.findViewById(R.id.textViewDate);
            user = itemView.findViewById(R.id.textViewUser);
            image = itemView.findViewById(R.id.imageMeetup);
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
        holder.title.setText("Meetup de React Native");
        holder.location.setText("Rua Dr. Getúlio Vargas, 2000");
        holder.date.setText("02 de Setembro, às 20h");
        holder.user.setText("Organizador: João Pedro Schmitz");
        holder.image.setImageResource(R.drawable.meetup);
    }

    @Override
    public int getItemCount() {
        return 6;
    }


}
