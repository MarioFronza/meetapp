package br.udesc.ddm.meetapp.retrofit;

import br.udesc.ddm.meetapp.service.FileService;
import br.udesc.ddm.meetapp.service.MeetupService;
import br.udesc.ddm.meetapp.service.SessionService;
import br.udesc.ddm.meetapp.service.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInitializer {
    private final Retrofit retrofit;

    public RetrofitInitializer() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.158:3333")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public UserService getUserService() {
        return retrofit.create(UserService.class);
    }

    public SessionService getSessionService() {
        return retrofit.create(SessionService.class);
    }

    public MeetupService getMeetupService() {
        return retrofit.create(MeetupService.class);
    }

    public FileService getFileService() {
        return retrofit.create(FileService.class);
    }

}
