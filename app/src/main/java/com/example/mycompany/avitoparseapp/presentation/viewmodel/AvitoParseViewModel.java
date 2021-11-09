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
    private MutableLiveData<Boolean> isInProgress = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsError() {
        return isError;
    }

    private MutableLiveData<Boolean> isError = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsInProgress2() {
        return isInProgress2;
    }

    private MutableLiveData<Boolean> isInProgress2 = new MutableLiveData<>();
    private MutableLiveData<List<CarCell>> carCellsData = new MutableLiveData<>();

    private List<CarCell> carFavoritesCell = new ArrayList<>();
    private MutableLiveData<List<CarCell>> carFavoritesCellsData = new MutableLiveData<>();

    private MutableLiveData<Car> carData = new MutableLiveData<>();
    private Disposable mDisposable;

    public AvitoParseViewModel(ParserRepository parserRepository) {
        this.parserRepository = parserRepository;
    }

    /**
     * Метод для получения списка объявлений автомобилей на основе переданных параметров
     * @param params - параметры для объявлений (марка, модель и тд).
     */
    public void loadCellsData(String params, boolean isProgressBarShowed) {
        isError.setValue(false);
        isInProgress.setValue(isProgressBarShowed);
        mDisposable = parserRepository.loadCarCells(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isInProgress.setValue(false))
                .subscribe(carCellsData::setValue, e -> isError.setValue(true));
    }

    /**
     * Метод для получения полного объявления автомобиля на основе объекта CarCell
     * @param carCell - выбранное объявление из списка, который был получен в loadCellsData(String params)
     */
    public void loadCarData(CarCell carCell) {
        isInProgress2.setValue(true);
        mDisposable = parserRepository.loadCarItem(carCell)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isInProgress2.setValue(false))
                .subscribe(carData::setValue, error -> System.out.println(error.getLocalizedMessage()));
    }

    public void loadCarFav() {
        carFavoritesCellsData.setValue(carFavoritesCell);
        isInProgress.setValue(false);
    }

    public void addCarToFavorites(CarCell carCell) {
        if(!carFavoritesCell.contains(carCell)) {
            carFavoritesCell.add(carCell);
            carFavoritesCellsData.postValue(carFavoritesCell);
        } else {
            carFavoritesCell.remove(carCell);
            carFavoritesCellsData.postValue(carFavoritesCell);
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
        this.carData = new MutableLiveData<>();
    }

    /**
     * Getter LiveData<Boolean></> для подписки
     * @return LiveData<Boolean>
     */
    public LiveData<Boolean> getIsInProgress() {
        return isInProgress;
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
    public LiveData<Car> getCarData() {
        return carData;
    }

    /**
     * Getter LiveData<List<CarCell>></> для подписки
     * @return LiveData<List<CarCell>>
     */
    public LiveData<List<CarCell>> getCarFavoritesCellsData() {
        return carFavoritesCellsData;
    }

}
