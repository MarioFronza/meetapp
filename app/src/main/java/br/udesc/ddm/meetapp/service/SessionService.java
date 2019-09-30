package br.udesc.ddm.meetapp.service;

import br.udesc.ddm.meetapp.model.Session;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SessionService {

    @POST("sessions")
    Call<Session> signIn(@Body Session session);

}
