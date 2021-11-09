package com.example.mycompany.avitoparseapp.data.repository;

import com.example.mycompany.avitoparseapp.data.parser.Parser;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;

import java.util.List;

import io.reactivex.Single;


public class ParserRepository {
    private Parser parser;

    public ParserRepository(Parser parser) {
        this.parser = parser;
    }

    public Single<List<CarCell>> loadCarCells(String params) {
        return Single.fromCallable(() -> parser.getCarCells(params));
    }

    public Single<Car> loadCarItem(CarCell carCell) {
        return Single.fromCallable(() -> parser.getCarInfo(carCell));
    }
}
