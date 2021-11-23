package com.example.mycompany.avitoparseapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;

@Database(entities = {CarCell.class, Car.class}, version = 2, exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase dbInstance;

    public abstract CarCellDAO getDao();
    public abstract CarDAO getCarDao();

    public static AppDatabase getAppDatabase(Context context) {
        if(dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppDb")
                    .allowMainThreadQueries()
                    .build();
        }
        return dbInstance;
    }
}
