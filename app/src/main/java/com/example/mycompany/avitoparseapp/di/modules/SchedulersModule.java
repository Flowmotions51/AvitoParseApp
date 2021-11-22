package com.example.mycompany.avitoparseapp.di.modules;

import com.example.mycompany.avitoparseapp.api.SchedulersProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulersModule {

    @Provides
    SchedulersProvider bindSchedulers() {
        return new SchedulersProvider();
    };
}
