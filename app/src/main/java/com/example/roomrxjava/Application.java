package com.example.roomrxjava;

import android.content.Context;

import com.example.roomrxjava.di.AppComponent;
import com.example.roomrxjava.di.AppModule;
import com.example.roomrxjava.di.DaggerAppComponent;

public class Application extends android.app.Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getComponent(Context context) {
        return ((Application) context.getApplicationContext()).component;
    }
}