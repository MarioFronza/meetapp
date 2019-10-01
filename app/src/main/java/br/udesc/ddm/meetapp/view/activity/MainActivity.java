package br.udesc.ddm.meetapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.view.fragment.InscriptionsFragment;
import br.udesc.ddm.meetapp.view.fragment.MeetupsFragment;
import br.udesc.ddm.meetapp.view.fragment.NewMeetupFragment;
import br.udesc.ddm.meetapp.view.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationViewEx bottomNavigationViewEx;
    private SharedPreferences preferences;
    private ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationConfig();
        setFirstFragment();
        enableNavigation(bottomNavigationViewEx);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("meetappPreferences", MODE_PRIVATE);

        imageButton = toolbar.findViewById(R.id.mainLogo);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void bottomNavigationConfig() {

        this.bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
        this.bottomNavigationViewEx.enableAnimation(false);
        this.bottomNavigationViewEx.enableShiftingMode(true);
        this.bottomNavigationViewEx.enableItemShiftingMode(false);
        this.bottomNavigationViewEx.setTextVisibility(true);

    }

    private void setFirstFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager, new MeetupsFragment()).commit();
    }

    private void enableNavigation(BottomNavigationViewEx viewEx) {
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.ic_meetups:
                        fragmentTransaction.replace(R.id.viewPager, new MeetupsFragment()).commit();
                        return true;
                    case R.id.ic_inscriptions:
                        fragmentTransaction.replace(R.id.viewPager, new InscriptionsFragment()).commit();
                        return true;
                    case R.id.ic_new:
                        fragmentTransaction.replace(R.id.viewPager, new NewMeetupFragment()).commit();
                        return true;
                    case R.id.ic_profile:
                        fragmentTransaction.replace(R.id.viewPager, new ProfileFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String token = preferences.getString("token", "");
        if (token.equals("")) {
            startActivity(new Intent(MainActivity.this, SigninActivity.class));
        }
    }
}
