package br.udesc.ddm.meetapp.service;

import java.util.List;

import br.udesc.ddm.meetapp.model.Meetup;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MeetupService {

    @GET("meetups/")
    Call<List<Meetup>> getMeetups();

    @POST("meetups/")
    Call<Meetup> createMeetupr(@Body Meetup user);

    @PUT("meetups/{id}")
    Call<Meetup> updateUser(@Path("id") int id, @Body Meetup user);

    @DELETE("meetups/{id}")
    Call<Meetup> deleteUser(@Path("id") int id);

}
