package br.udesc.ddm.meetapp.service;

import br.udesc.ddm.meetapp.model.Image;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileService {

    @Multipart
    @POST("/files")
    Call<Image> createFile(@Header("Authorization") String authHeader, @Part MultipartBody.Part image);



}
