package br.udesc.ddm.meetapp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.User;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import br.udesc.ddm.meetapp.view.activity.SigninActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {


    private SharedPreferences preferences;

    private Button signOutButton;
    private Button updateUserDataButton;

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;


    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preferences = this.getActivity().getSharedPreferences("meetappPreferences", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        signOutButton = view.findViewById(R.id.exitAppProfile);
        updateUserDataButton = view.findViewById(R.id.saveProfileButton);

        editTextName = view.findViewById(R.id.profileEditTextName);
        editTextEmail = view.findViewById(R.id.profileEditTextEmail);
        editTextCurrentPassword = view.findViewById(R.id.profileEditTextCurrentPass);
        editTextNewPassword = view.findViewById(R.id.profileEditTextNewPass);
        editTextConfirmPassword = view.findViewById(R.id.profileEditTextConfirmPass);

        requestUserData();

        updateUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = preferences.getString("token", "");
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String oldPassowrd = editTextCurrentPassword.getText().toString();
                String password = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                JSONObject json = new JSONObject();
                try {
                    json.put("name", name);
                    json.put("email", email);
                    if (!oldPassowrd.equals("")) {
                        json.put("oldPassowrd", oldPassowrd);
                        json.put("password", password);
                        json.put("confirmPassword", confirmPassword);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
                Call<User> call = new RetrofitInitializer().getUserService().updateUser("Bearer " + token, body);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Dados alterados com sucesso!", Toast.LENGTH_LONG).show();
                        } else {
                            JSONObject jObjError;
                            try {
                                jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getActivity(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                            } catch (JSONException | IOException e) {
                                Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putString("token", "").apply();
                startActivity(new Intent(getActivity(), SigninActivity.class));
            }
        });

        return view;
    }

    private void requestUserData() {
        String token = preferences.getString("token", "");
        Call<User> call = new RetrofitInitializer().getUserService().getUser("Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    editTextName.setText(user.getName());
                    editTextEmail.setText(user.getEmail());
                } else {
                    JSONObject jObjError;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    } catch (JSONException | IOException e) {
                        Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

    }


}
