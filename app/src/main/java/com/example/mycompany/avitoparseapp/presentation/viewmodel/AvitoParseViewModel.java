package com.example.mycompany.avitoparseapp.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycompany.avitoparseapp.data.repository.ParserRepository;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AvitoParseViewModel extends ViewModel {
    private ParserRepository parserRepository;
    private MutableLiveData<Boolean> isInProgress = new MutableLiveData<>();
    private MutableLiveData<List<CarCell>> carCellsData = new MutableLiveData<>();

    private MutableLiveData<Car> carData = new MutableLiveData<>();
    private Disposable mDisposable;

    public AvitoParseViewModel(ParserRepository parserRepository) {
        this.parserRepository = parserRepository;
    }

    public void loadCellsData(String params) {
        mDisposable = parserRepository.loadCarCellsAsyncRx(params)
                .doOnSubscribe(disposable -> {
                    isInProgress.postValue(true);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isInProgress.postValue(false))
                .subscribe(carCellsData::setValue, error -> System.out.println(error.getLocalizedMessage()));
    }

    public void loadCarDataSync(CarCell carCell) {
        mDisposable = parserRepository.loadCarItemAsyncRx(carCell)
                .doOnSubscribe(disposable -> {
                    isInProgress.postValue(true);
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> isInProgress.postValue(false))
                .subscribe(carData::setValue, error -> System.out.println(error.getLocalizedMessage()));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    //
    public void resetLiveDataCarCells() {
        this.carCellsData = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsInProgress() {
        return isInProgress;
    }

    public MutableLiveData<List<CarCell>> getCarCellsData() {
        return carCellsData;
    }

    public MutableLiveData<Car> getCarData() {
        return carData;
    }

}
