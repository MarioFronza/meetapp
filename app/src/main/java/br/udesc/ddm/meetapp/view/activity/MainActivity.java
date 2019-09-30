package br.udesc.ddm.meetapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.view.fragment.InscriptionsFragment;
import br.udesc.ddm.meetapp.view.fragment.MeetupsFragment;
import br.udesc.ddm.meetapp.view.fragment.NewMeetupFragment;
import br.udesc.ddm.meetapp.view.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationConfig();
        setFirstFragment();
        enableNavigation(bottomNavigationViewEx);
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
}
