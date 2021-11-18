package com.example.mycompany.avitoparseapp.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycompany.avitoparseapp.data.repository.ParserRepository;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AvitoParseViewModel extends ViewModel {
    private ParserRepository parserRepository;

    private MutableLiveData<List<CarCell>> carCellsData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInProgressCellsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtCellsLoading = new MutableLiveData<>();

    private MutableLiveData<Car> carItemData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isInProgressItemLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtItemLoading = new MutableLiveData<>();

    private MutableLiveData<Car> carItemDataFavorites = new MutableLiveData<>();
    private MutableLiveData<Boolean> isErrorAtFavoriteItemLoading = new MutableLiveData<>();

    private List<CarCell> carCellsFavorites = new ArrayList<>();
    private MutableLiveData<List<CarCell>> carFavoritesCellsData = new MutableLiveData<>();



    private Disposable mDisposable;

    public AvitoParseViewModel(ParserRepository parserRepository) {
        this.parserRepository = parserRepository;
    }

    /**
     * Метод для получения списка объявлений автомобилей на основе переданных параметров
     * @param params - параметры для объявлений (марка, модель и тд).
     */
    public void loadCellsData(String params, boolean isProgressBarShowed) {
        isErrorAtCellsLoading.setValue(false);
        isInProgressCellsLoading.setValue(isProgressBarShowed);
        mDisposable = parserRepository.loadCarCells(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isInProgressCellsLoading.setValue(false))
                .subscribe(carCellsData::setValue, e -> isErrorAtCellsLoading.setValue(true));
    }

    /**
     * Метод для получения полного объявления автомобиля на основе объекта CarCell
     * @param carCell - выбранное объявление из списка, который был получен в loadCellsData(String params)
     */
    public void loadCarData(CarCell carCell) {
        isErrorAtItemLoading.setValue(false);
        isInProgressItemLoading.setValue(true);
        mDisposable = parserRepository.loadCarItem(carCell)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isInProgressItemLoading.setValue(false))
                .subscribe(carItemData::setValue, error -> isErrorAtItemLoading.setValue(true));
    }

    /**
     * Метод для получения полного объявления автомобиля на основе объекта CarCell
     * @param carCell - выбранное объявление из списка, который был получен в loadCellsData(String params)
     */
    public void loadCarFavData(CarCell carCell) {
        isErrorAtFavoriteItemLoading.setValue(false);
        isInProgressItemLoading.setValue(true);
        mDisposable = parserRepository.loadCarItem(carCell)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isInProgressItemLoading.setValue(false))
                .subscribe(carItemDataFavorites::setValue, error -> isErrorAtFavoriteItemLoading.setValue(true));
    }

    public void loadCarFav() {
        carFavoritesCellsData.setValue(carCellsFavorites);
        isInProgressCellsLoading.setValue(false);
    }

    public void addCarToFavorites(CarCell carCell) {
        if(!carCellsFavorites.contains(carCell)) {
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
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    //todo Без этого метода возникает дефект при открытии во второй раз CarCellsFragment
    public void resetLiveDataCarCells() {
        this.carCellsData = new MutableLiveData<>();
    }

    public void resetLiveDataCarItem() {
        this.carItemData = new MutableLiveData<>();
    }

    /**
     * Getter LiveData<Boolean></> для подписки
     * @return LiveData<Boolean>
     */
    public LiveData<Boolean> getIsInProgressCellsLoading() {
        return isInProgressCellsLoading;
    }

    /**
     * Getter LiveData<List<CarCell>></> для подписки
     * @return LiveData<List<CarCell>>
     */
    public LiveData<List<CarCell>> getCarCellsData() {
        return carCellsData;
    }

    /**
     * Getter LiveData<Car></> для подписки
     * @return LiveData<Car>
     */
    public LiveData<Car> getCarItemData() {
        return carItemData;
    }

    /**
     * Getter LiveData<List<CarCell>></> для подписки
     * @return LiveData<List<CarCell>>
     */
    public LiveData<List<CarCell>> getCarFavoritesCellsData() {
        return carFavoritesCellsData;
    }

    public LiveData<Car> getCarItemDataFavorites() {
        return carItemDataFavorites;
    }

    public LiveData<Boolean> getIsErrorAtCellsLoading() {
        return isErrorAtCellsLoading;
    }

    public LiveData<Boolean> getIsErrorAtItemLoading() {
        return isErrorAtItemLoading;
    }

    public LiveData<Boolean> getIsErrorAtFavoriteItemLoading() {
        return isErrorAtFavoriteItemLoading;
    }

    public MutableLiveData<Boolean> getIsInProgressItemLoading() {
        return isInProgressItemLoading;
    }

}
