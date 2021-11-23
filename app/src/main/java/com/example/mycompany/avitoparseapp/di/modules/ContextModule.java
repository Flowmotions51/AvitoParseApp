package com.example.mycompany.avitoparseapp.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
//    private final Context context;
//
//    public ContextModule(Context context) {
//        this.context = context;
//    }
//
//    @Binds
//    abstract Context bindContext(Application application);

    Application mApplication;

    public ContextModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}
