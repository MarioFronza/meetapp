package br.udesc.ddm.meetapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import br.udesc.ddm.meetapp.model.User;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import br.udesc.ddm.meetapp.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button signupButton;
    private ProgressBar progressBar;

    private Call<User> call;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName = findViewById(R.id.signupEditTextName);
        editTextEmail = findViewById(R.id.signupEditTextEmail);
        editTextPassword = findViewById(R.id.signupEditTextPass);
        signupButton = findViewById(R.id.signupButton);
        progressBar = findViewById(R.id.signupProgressBar);

        progressBar.setVisibility(View.INVISIBLE);
    }

    public void signup(View view) {
        visibilityProgressSigup(true);

        user = new User(
                editTextName.getText().toString(),
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString()
        );

        call = new RetrofitInitializer().getUserService().createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    visibilityProgressSigup(false);
                    startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                } else {
                    JSONObject jObjError;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(SignupActivity.this, jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    visibilityProgressSigup(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Erro na requisição", Toast.LENGTH_LONG).show();
                visibilityProgressSigup(false);
            }
        });
    }

    public void goToSignInActivity(View view) {
        startActivity(new Intent(SignupActivity.this, SigninActivity.class));
    }

    private void visibilityProgressSigup(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            signupButton.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            signupButton.setVisibility(View.VISIBLE);
        }
    }

}
