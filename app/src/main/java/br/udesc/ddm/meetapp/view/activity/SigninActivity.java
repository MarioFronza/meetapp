package br.udesc.ddm.meetapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Session;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button signinButton;
    private ProgressBar progressBar;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editTextEmail = findViewById(R.id.signinEditTextEmail);
        editTextPassword = findViewById(R.id.signinEditTextPass);
        signinButton = findViewById(R.id.signinButton);
        progressBar = findViewById(R.id.signinProgressBar);

        progressBar.setVisibility(View.INVISIBLE);

    }

    public void siginIn(View view) {
        visibilityProgressSigin(true);

        Session session = new Session(
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString()
        );

        Call<Session> call = new RetrofitInitializer().getSessionService().signIn(session);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if (response.isSuccessful()) {
                    visibilityProgressSigin(false);

                    preferences = getSharedPreferences("meetappPreferences", MODE_PRIVATE);
                    preferences.edit().putString("token", response.body().getToken()).apply();
                    startActivity(new Intent(SigninActivity.this, MainActivity.class));
                } else {
                    JSONObject jObjError;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(SigninActivity.this, jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    visibilityProgressSigin(false);
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                Toast.makeText(SigninActivity.this, "Erro na requisição", Toast.LENGTH_LONG).show();
                visibilityProgressSigin(false);
            }
        });

    }

    public void goToSiginUp(View view) {
        startActivity(new Intent(SigninActivity.this, SignupActivity.class));
    }


    private void visibilityProgressSigin(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            signinButton.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            signinButton.setVisibility(View.VISIBLE);
        }
    }
}
