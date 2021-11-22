package com.example.mycompany.avitoparseapp.data.repository;

import com.example.mycompany.avitoparseapp.api.ApiService;
import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.data.model.GetItemsResponse;
import com.example.mycompany.avitoparseapp.data.model.CarItemResponse;
import com.example.mycompany.avitoparseapp.data.model.Model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ApiRepository implements IApiRepository {

    private ApiService apiService;

    @Inject
    public ApiRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<GetItemsResponse> getCarCells(String brand, String model) {
        return apiService.getResponseItemList(brand, model);
    }

    public Single<CarItemResponse> getCar(String link) {
        return apiService.getItem(link);
    }

    public Single<List<Brand>> getBrandList() {
        return apiService.getBrandList();
    }

    public Single<List<Model>> getModelList(String brand) {
        return apiService.getModelsList(brand);
    }
}
