package br.udesc.ddm.meetapp.view.fragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.udesc.ddm.meetapp.R;
import br.udesc.ddm.meetapp.model.Image;
import br.udesc.ddm.meetapp.model.Meetup;
import br.udesc.ddm.meetapp.model.User;
import br.udesc.ddm.meetapp.retrofit.RetrofitInitializer;
import br.udesc.ddm.meetapp.view.activity.SignupActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMeetupFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private SharedPreferences preferences;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextLocation;
    private TextView textViewDate;

    private Button selectImageButton;
    private Button saveMeetupButton;

    private Bitmap bitmap;
    private File file = null;
    private String strDate;
    private String token;

    private int imageId = 0;

    private static final int IMAGE_REQUEST = 777;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_meetup, container, false);

        preferences = this.getActivity().getSharedPreferences("meetappPreferences", Context.MODE_PRIVATE);

        editTextTitle = view.findViewById(R.id.newMeetupEditTextTitle);
        editTextDescription = view.findViewById(R.id.newMeetupEditTextDescription);
        editTextLocation = view.findViewById(R.id.newMeetupEditTextLocation);
        textViewDate = view.findViewById(R.id.newMeetupTextViewDate);

        selectImageButton = view.findViewById(R.id.selectImageButton);
        saveMeetupButton = view.findViewById(R.id.saveMeetupButton);

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
            }
        });

        saveMeetupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file != null) {
                    token = preferences.getString("token", "");
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName() + ".jpeg", requestFile);


                    Call<Image> call = new RetrofitInitializer().getFileService().createFile("Bearer " + token, body);
                    call.enqueue(new Callback<Image>() {
                        @Override
                        public void onResponse(Call<Image> call, Response<Image> response) {
                            if (response.isSuccessful()) {
                                Image image = response.body();
                                imageId = image.getId();
                            } else {
                                Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Image> call, Throwable t) {
                            Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                            t.printStackTrace();
                        }
                    });

                }

                if (imageId != 0) {
                    String title = editTextTitle.getText().toString();
                    String description = editTextDescription.getText().toString();
                    String location = editTextLocation.getText().toString();
                    String date = strDate;
                    JSONObject json = new JSONObject();
                    try {
                        json.put("title", title);
                        json.put("description", description);
                        json.put("location", location);
                        json.put("date", date);
                        json.put("file_id", imageId);
                        Toast.makeText(getActivity(), json.toString(), Toast.LENGTH_LONG).show();
                        RequestBody meetupBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
                        Call<ResponseBody> meetupCall = new RetrofitInitializer().getMeetupService().createMeetup("Bearer " + token, meetupBody);
                        meetupCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Sucesso ao cadastrar Meetup", Toast.LENGTH_LONG).show();
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
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getActivity(), "Erro na requisição", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Selecione uma imagem para o Meetup", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            file = new File(getActivity().getCacheDir(), "meetup-image");
            try {
                file.createNewFile();
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            selectImageButton.setText(R.string.change_image);
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String formatedDatePicker = dayOfMonth + "/" + month + "/" + year;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = sdf.format(calendar.getTime());
        Date date = null;
        try {
            date = sdf.parse(formatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        strDate = date.toString();
        textViewDate.setText(formatedDatePicker);
    }
}
