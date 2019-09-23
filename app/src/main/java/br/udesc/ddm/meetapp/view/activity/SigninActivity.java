package br.udesc.ddm.meetapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.udesc.ddm.meetapp.R;

public class SigninActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

    }

    public void goToCreateAccount(View view){
        startActivity(new Intent(SigninActivity.this, SignupActivity.class));
    }

    public void siginIn(View view){
        startActivity(new Intent(SigninActivity.this, MainActivity.class));
    }
}
