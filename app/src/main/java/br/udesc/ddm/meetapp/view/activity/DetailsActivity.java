package br.udesc.ddm.meetapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.util.DownloadImageTask;

public class DetailsActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewLocation;
    private TextView textViewDate;
    private TextView textViewUser;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        textViewTitle = findViewById(R.id.textViewDetailsTitle);
        textViewDescription = findViewById(R.id.textViewDetailsDescriptio);
        textViewLocation = findViewById(R.id.textViewDetailsLocation);
        textViewDate = findViewById(R.id.textViewDetailsDate);
        textViewUser = findViewById(R.id.textViewDetailsUser);
        imageView = findViewById(R.id.imageDetailsMeetup);

        Meetup meetup = (Meetup) getIntent().getSerializableExtra("meetup");

        textViewTitle.setText(meetup.getTitle());
        textViewDescription.setText(meetup.getDescription());
        textViewLocation.setText(meetup.getLocation());
        textViewDate.setText(formatDate(meetup.getDate()));
        textViewUser.setText("Organizador: " + meetup.getUser().getName());
        new DownloadImageTask(imageView).execute(meetup.getImage().getUrl());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
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


}
