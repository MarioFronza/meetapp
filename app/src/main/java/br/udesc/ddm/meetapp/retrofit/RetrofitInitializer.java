package br.udesc.ddm.meetapp.retrofit;

import br.udesc.ddm.meetapp.service.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInitializer {
    private final Retrofit retrofit;

    public RetrofitInitializer() {
        retrofit = new Retrofit.Builder().baseUrl("http://10.15.68.144:3333")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public UserService getUserService() {
        return retrofit.create(UserService.class);
    }

}
