package com.example.mycompany.avitoparseapp.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.mycompany.avitoparseapp.database.AppDatabase;
import com.example.mycompany.avitoparseapp.database.DAO;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {

    @Singleton
    @Provides
    static DAO getDao(AppDatabase appDatabase) {
        return appDatabase.getDao();
    }

    @Singleton
    @Provides
    static AppDatabase getDatabaseInstance(Application context) {
        return AppDatabase.getAppDatabase(context);
    }
}
