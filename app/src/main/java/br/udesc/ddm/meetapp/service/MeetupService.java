package br.udesc.ddm.meetapp.service;

import java.util.List;

import br.udesc.ddm.meetapp.model.Meetup;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MeetupService {

    @GET("meetups/")
    Call<List<Meetup>> getMeetups(@Header("Authorization") String authHeader);

    @GET("meetups/")
    Call<List<Meetup>> getMeetupsWithDate(@Header("Authorization") String authHeader, @Query("date") String date);

    @POST("meetups/")
    Call<ResponseBody> createMeetup(@Header("Authorization") String authHeader, @Body RequestBody meetup);

    @PUT("meetups/{id}")
    Call<Meetup> updateUser(@Path("id") int id, @Body Meetup user);

    @DELETE("meetups/{id}")
    Call<Meetup> deleteUser(@Path("id") int id);

}
