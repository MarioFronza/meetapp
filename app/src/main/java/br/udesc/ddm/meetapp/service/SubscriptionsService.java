package br.udesc.ddm.meetapp.service;

import java.util.List;

import br.udesc.ddm.meetapp.model.Meetup;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SubscriptionsService {

    @GET("subscriptions/")
    Call<List<Meetup>> getSubscriptions(@Header("Authorization") String authHeader);

    @POST("subscriptions/{id}")
    Call<ResponseBody> createSubscriptions(@Header("Authorization") String authHeader, @Path("id") int id);

}
