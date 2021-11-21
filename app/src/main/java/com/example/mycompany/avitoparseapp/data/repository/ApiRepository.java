package com.example.mycompany.avitoparseapp.data.repository;

import com.example.mycompany.avitoparseapp.api.ApiService;
import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.data.model.GetItemsResponse;
import com.example.mycompany.avitoparseapp.data.model.ItemResponse;
import com.example.mycompany.avitoparseapp.data.model.Model;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ApiRepository {

    private ApiService apiService;

    @Inject
    public ApiRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<GetItemsResponse> itemSingle(String brand, String model) {
        return apiService.getResponseItemList(brand, model);
    }

    public Single<ItemResponse> itemCar(String link) {
        return apiService.getItem(link);
    }

    public Single<List<Brand>> brandList() {
        return apiService.getBrandList();
    }

    public Single<List<Model>> modelsList(String brand) {
        return apiService.getModelsList(brand);
    }
}
