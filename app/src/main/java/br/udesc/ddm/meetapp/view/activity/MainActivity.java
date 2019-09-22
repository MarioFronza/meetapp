package br.udesc.ddm.meetapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import br.udesc.ddm.meetapp.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationViewEx bottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationConfig();
    }


    public void bottomNavigationConfig() {

        this.bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
        this.bottomNavigationViewEx.enableAnimation(false);
        this.bottomNavigationViewEx.enableShiftingMode(true);
        this.bottomNavigationViewEx.enableItemShiftingMode(false);
        this.bottomNavigationViewEx.setTextVisibility(true);

    }
}
