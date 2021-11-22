package com.example.mycompany.avitoparseapp.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.data.model.GetItemsResponse;
import com.example.mycompany.avitoparseapp.data.model.Model;
import com.example.mycompany.avitoparseapp.data.repository.ApiRepository;
import com.example.mycompany.avitoparseapp.api.SchedulersProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AvitoParseViewModel extends ViewModel {
    /**
     * CarBrandPickerFragment liveData
     */
    private MutableLiveData<List<Brand>> brandListData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInProgressBrandListLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtBrandListLoading = new MutableLiveData<>();

    public LiveData<List<Brand>> getBrandListData() {
        return brandListData;
    }
    public LiveData<Boolean> getIsInProgressBrandListLoading() {
        return isInProgressBrandListLoading;
    }
    public LiveData<Boolean> getIsErrorAtBrandListLoading() {
        return isErrorAtBrandListLoading;
    }

    /**
     * CarModelPickerFragment liveData
     */
    private MutableLiveData<List<Model>> modelsListData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInProgressModelsListLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtModelsListLoading = new MutableLiveData<>();

    public LiveData<List<Model>> getModelsListData() {
        return modelsListData;
    }
    public LiveData<Boolean> getIsInProgressModelsListLoading() {
        return isInProgressModelsListLoading;
    }
    public LiveData<Boolean> getIsErrorAtModelsListLoading() {
        return isErrorAtModelsListLoading;
    }

    /**
     * CarCellsFragment liveData
     */
    private MutableLiveData<GetItemsResponse> carCellsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInProgressCellsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtCellsLoading = new MutableLiveData<>();

    public LiveData<GetItemsResponse> getCarCellsMutableLiveData() {
        return carCellsMutableLiveData;
    }
    public LiveData<Boolean> getIsInProgressCellsLoading() {
        return isInProgressCellsLoading;
    }
    public LiveData<Boolean> getIsErrorAtCellsLoading() {
        return isErrorAtCellsLoading;
    }

    /**
     * CarItemFragment liveData
     */
    private MutableLiveData<Car> carItemData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInProgressItemLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtItemLoading = new MutableLiveData<>();

    public LiveData<Car> getCarItemData() {
        return carItemData;
    }
    public MutableLiveData<Boolean> getIsInProgressItemLoading() {
        return isInProgressItemLoading;
    }
    public LiveData<Boolean> getIsErrorAtItemLoading() {
        return isErrorAtItemLoading;
    }

    /**
     * CarFavoritesFragment liveData
     */
    private List<CarCell> carCellsFavorites = new ArrayList<>();
    private MutableLiveData<List<CarCell>> carFavoritesCellsData = new MutableLiveData<>();

    public List<CarCell> getCarCellsFavorites() {
        return carCellsFavorites;
    }
    public void setCarCellsFavorites(List<CarCell> carCellsFavorites) {
        this.carCellsFavorites = carCellsFavorites;
    }
    public LiveData<List<CarCell>> getCarFavoritesCellsData() {
        return carFavoritesCellsData;
    }

    /**
     * CarItemFavoritesFragment liveData
     */
    private MutableLiveData<Car> carItemDataFavorites = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInProgressFavoriteItemLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtFavoriteItemLoading = new MutableLiveData<>();

    public LiveData<Car> getCarItemDataFavorites() {
        return carItemDataFavorites;
    }
    public MutableLiveData<Boolean> getIsInProgressFavoriteItemLoading() {
        return isInProgressFavoriteItemLoading;
    }
    public LiveData<Boolean> getIsErrorAtFavoriteItemLoading() {
        return isErrorAtFavoriteItemLoading;
    }


    private ApiRepository apiRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SchedulersProvider schedulersProvider;


    @Inject
    public AvitoParseViewModel(ApiRepository apiRepository, SchedulersProvider schedulersProvider) {
        this.apiRepository = apiRepository;
        this.schedulersProvider = schedulersProvider;
    }

    public void loadBrandsData() {
        compositeDisposable.add(apiRepository.getBrandList()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressBrandListLoading.setValue(false))
                .subscribe(val -> brandListData.setValue(val),
                        e -> isErrorAtBrandListLoading.setValue(true)));
    }

    public void loadModelsData(String brand) {
        isErrorAtModelsListLoading.setValue(false);
        isInProgressModelsListLoading.setValue(true);
        compositeDisposable.add(apiRepository.getModelList(brand)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressModelsListLoading.setValue(false))
                .subscribe(val -> modelsListData.setValue(val),
                        e -> isErrorAtModelsListLoading.setValue(true)));
    }

    /**
     * Метод для получения списка объявлений автомобилей на основе переданных параметров
     *
     */
    public void loadCarCellsData(String brand, String model, boolean isProgressBarShowed) {
        isErrorAtCellsLoading.setValue(false);
        isInProgressCellsLoading.setValue(isProgressBarShowed);
                compositeDisposable.add(apiRepository.getCarCells(brand, model)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressCellsLoading.setValue(false))
                .subscribe(val -> carCellsMutableLiveData.setValue(val),
                        e -> isErrorAtCellsLoading.setValue(true)));
    }

    /**
     * Метод для получения полного объявления автомобиля на основе объекта CarCell
     *
     * @param carCell - выбранное объявление из списка, который был получен в loadCellsData(String params)
     */
    public void loadCarItemData(CarCell carCell) {
        //todo
        String link = carCell.getLinkToItem().substring(carCell.getLinkToItem().lastIndexOf('/') + 1);
        isInProgressItemLoading.setValue(true);
        isErrorAtItemLoading.setValue(false);
        compositeDisposable.add(apiRepository.getCar(link)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressItemLoading.setValue(false))
                .subscribe(val -> carItemData.setValue(val),
                        e -> isErrorAtItemLoading.setValue(true)));
    }

    /**
     * Метод для получения полного объявления автомобиля на основе объекта CarCell
     *
     * @param carCell - выбранное объявление из списка, который был получен в loadCellsData(String params)
     */
    public void loadCarItemFavoriteData(CarCell carCell) {
        String link = carCell.getLinkToItem().substring(carCell.getLinkToItem().lastIndexOf('/') + 1);
        isErrorAtFavoriteItemLoading.setValue(false);
        isInProgressFavoriteItemLoading.setValue(true);
        compositeDisposable.add(apiRepository.getCar(link)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressFavoriteItemLoading.setValue(false))
                .subscribe(val -> carItemDataFavorites.setValue(val),
                        e -> isErrorAtFavoriteItemLoading.setValue(true)));
    }

    public void loadCarFav() {
        carFavoritesCellsData.setValue(carCellsFavorites);
        isInProgressCellsLoading.setValue(false);
    }

    public void addCarToFavorites(CarCell carCell) {
        if (!carCellsFavorites.contains(carCell)) {
            carCellsFavorites.add(carCell);
            carFavoritesCellsData.postValue(carCellsFavorites);
        } else {
            carCellsFavorites.remove(carCell);
            carFavoritesCellsData.postValue(carCellsFavorites);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }
}
