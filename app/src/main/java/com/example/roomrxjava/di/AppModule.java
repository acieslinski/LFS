package com.example.roomrxjava.di;

import android.content.Context;

import com.example.roomrxjava.remote.Api;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private Context context;

    public AppModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Singleton
    @Provides
    public Context getAppContext(){
        return  context;
    }

    @Singleton
    @Provides
    public Executor getExecutor(){
        return  Executors.newFixedThreadPool(2);
    }

    @Singleton
    @Provides
    public Retrofit getRemoteClient(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Singleton
    @Provides
    public Api getItemClient(Retrofit retrofit){
        return retrofit.create(Api.class);
    }
}
