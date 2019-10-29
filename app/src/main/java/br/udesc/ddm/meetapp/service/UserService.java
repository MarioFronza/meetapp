package br.udesc.ddm.meetapp.service;

import br.udesc.ddm.meetapp.model.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    @GET("users/")
    Call<User> getUser(@Header("Authorization") String authHeader);

    @POST("users/")
    Call<User> createUser(@Body User user);

    @PUT("users/")
    Call<User> updateUser(
            @Header("Authorization") String authHeader, @Body RequestBody user);

}
