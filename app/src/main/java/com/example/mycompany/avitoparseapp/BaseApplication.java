package com.example.mycompany.avitoparseapp;

import android.app.Application;

import com.example.mycompany.avitoparseapp.di.components.AppComponent;
import com.example.mycompany.avitoparseapp.di.components.DaggerAppComponent;

public class BaseApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
