package com.example.mycompany.avitoparseapp.di.modules;

import android.app.Application;

import com.example.mycompany.avitoparseapp.database.AppDatabase;
import com.example.mycompany.avitoparseapp.database.CarCellDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {

    @Singleton
    @Provides
    static CarCellDAO getDao(AppDatabase appDatabase) {
        return appDatabase.getDao();
    }

    @Singleton
    @Provides
    static AppDatabase getDatabaseInstance(Application context) {
        return AppDatabase.getAppDatabase(context);
    }
}
