package com.example.mycompany.avitoparseapp.data.repository;

import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.database.CarCellDAO;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DataBaseRepository {
    private CarCellDAO carCellDAO;

    @Inject
    DataBaseRepository(CarCellDAO carCellDAO) {
        this.carCellDAO = carCellDAO;
    }

    public Single<List<CarCell>> getAllFavoritesCarCells() {
        return carCellDAO.getAllRecordsFromDb();
    }

    public Single<Integer> checkIfCarCellExistInFavorites(CarCell cell) {
        return carCellDAO.selectCountByLinkItem(cell.getLinkToItem());
    }

    public Completable insertOrDeleteIfExist(CarCell cell) {
        return carCellDAO.selectCountByLinkItem(cell.getLinkToItem())
                .flatMapCompletable(integer -> {
                    if (integer == 0) {
                        return carCellDAO.insertRecord(cell);
                    } else {
                        return carCellDAO.deleteByLink(cell.getLinkToItem());
                    }
                });
    }
}
