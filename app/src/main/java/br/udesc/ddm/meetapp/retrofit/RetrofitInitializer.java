package br.udesc.ddm.meetapp.retrofit;

import br.udesc.ddm.meetapp.service.FileService;
import br.udesc.ddm.meetapp.service.SubscriptionsService;
import br.udesc.ddm.meetapp.service.MeetupService;
import br.udesc.ddm.meetapp.service.SessionService;
import br.udesc.ddm.meetapp.service.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {
    private final Retrofit retrofit;

//    public RetrofitInitializer() {
//       retrofit = new Retrofit.Builder().baseUrl("https://ddm-meetapp-server.herokuapp.com")
//                .addConverterFactory(GsonConverterFactory.create()).build();
//    }

    public RetrofitInitializer() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.106:3333")
                .addConverterFactory(GsonConverterFactory.create()).build();
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

    public SubscriptionsService getSubscriptionsService() {
        return retrofit.create(SubscriptionsService.class);
    }

}
