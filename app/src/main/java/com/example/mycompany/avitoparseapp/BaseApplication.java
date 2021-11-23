package com.example.mycompany.avitoparseapp;

import android.app.Application;

import com.example.mycompany.avitoparseapp.di.components.AppComponent;
import com.example.mycompany.avitoparseapp.di.components.DaggerAppComponent;
import com.example.mycompany.avitoparseapp.di.modules.ContextModule;


public class BaseApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().contextModule(new ContextModule(this))
        .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
