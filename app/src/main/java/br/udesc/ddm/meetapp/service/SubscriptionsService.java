package br.udesc.ddm.meetapp.service;

import java.util.List;

import br.udesc.ddm.meetapp.model.Meetup;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SubscriptionsService {

    @GET("subscriptions/")
    Call<List<Meetup>> getSubscriptions(@Header("Authorization") String authHeader);

}
