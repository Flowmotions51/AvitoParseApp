package com.example.mycompany.avitoparseapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycompany.avitoparseapp.data.model.CarCell;

import java.util.List;

@Dao
public interface CarCellDAO {

    @Query("SELECT * FROM CARCELL ORDER BY id DESC")
    List<CarCell> getAllRecordsFromDb();

    @Insert
    void insertRecord(CarCell carCellEntity);

    @Query("DELETE FROM CARCELL WHERE linkToItem = :link")
    void deleteByLink(String link);

    @Query("SELECT COUNT(*) FROM CARCELL WHERE linkToItem = :link")
    int selectCountByLinkItem(String link);

    @Query("SELECT carId FROM CARCELL WHERE linkToItem = :link")
    int selectCarIdByLinkItem(String link);

}
