package com.example.mycompany.avitoparseapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CarDAO {

    @Query("SELECT * FROM CARITEM ORDER BY id DESC")
    List<Car> getAllRecordsFromDb();

    @Query("SELECT id FROM CARITEM WHERE linkToItem = :linkToItem")
    int getIdFromDbBy(String linkToItem);

    @Insert
    void insertRecord(Car carCellEntity);

    @Query("DELETE FROM CARITEM WHERE id = :carId")
    void deleteByCarId(int carId);
}
