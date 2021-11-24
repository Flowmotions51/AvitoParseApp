package com.example.mycompany.avitoparseapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mycompany.avitoparseapp.data.model.CarCell;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface CarCellDAO {

    @Query("SELECT * FROM CARCELL ORDER BY id DESC")
    Single<List<CarCell>> getAllRecordsFromDb();

    @Insert
    Completable insertRecord(CarCell carCellEntity);

    @Query("DELETE FROM CARCELL WHERE linkToItem = :link")
    Completable deleteByLink(String link);

    @Query("SELECT COUNT(*) FROM CARCELL WHERE linkToItem = :link")
    Single<Integer> selectCountByLinkItem(String link);

//    @Query("SELECT carId FROM CARCELL WHERE linkToItem = :link")
//    int selectCarIdByLinkItem(String link);

}
