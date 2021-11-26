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

    /**
     * Получить все объекты CarCell из таблицы
     * @return
     */
    public Single<List<CarCell>> getAllFavoritesCarCells() {
        return carCellDAO.getAllRecordsFromDb();
    }

    /**
     * Проверка наличия объекта CarCell в БД по значению linkToItem
     * @param cell
     * @return
     */
    public Single<Integer> checkIfCarCellExistInFavorites(CarCell cell) {
        return carCellDAO.selectCountByLinkItem(cell.getLinkToItem());
    }

    /**
     * Если в БД нет объекта CarCell со значением linkToItem, то добавляем его, если есть, удаляем
     * @param cell
     * @return
     */
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
