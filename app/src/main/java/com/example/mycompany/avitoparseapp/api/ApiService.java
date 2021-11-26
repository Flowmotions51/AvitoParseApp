package com.example.mycompany.avitoparseapp.api;

import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.data.model.Model;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    /**
     * Получить список объявлений на основе брэнда и модели
     * @param brand
     * @param model
     * @return
     */
    @GET("getCells/{brand}/{model}")
    Single<List<CarCell>> getResponseItemList(@Path("brand")String brand, @Path("model")String model);

    /**
     * Получить объявление на основе ссылки
     * @param link
     * @return
     */
    @GET("getItem/{link}")
    Single<Car> getItem(@Path("link")String link);

    /**
     * Получить список брендов
     * @return
     */
    @GET("getBrandList")
    Single<List<Brand>> getBrandList();

    /**
     * Получить список моделей бренда
     * @param brand
     * @return
     */
    @GET("getModelsList/{brand}")
    Single<List<Model>> getModelsList(@Path("brand")String brand);
}
