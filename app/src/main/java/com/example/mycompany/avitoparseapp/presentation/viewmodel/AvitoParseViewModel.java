package com.example.mycompany.avitoparseapp.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycompany.avitoparseapp.api.SchedulersProvider;
import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.data.model.Model;
import com.example.mycompany.avitoparseapp.data.repository.ApiRepository;
import com.example.mycompany.avitoparseapp.data.repository.DataBaseRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class AvitoParseViewModel extends ViewModel {

    /**
     * CarBrandPickerFragment liveData
     */
    private final MutableLiveData<List<Brand>> brandListData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInProgressBrandListLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isErrorAtBrandListLoading = new MutableLiveData<>();

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
    private final MutableLiveData<List<Model>> modelsListData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInProgressModelsListLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isErrorAtModelsListLoading = new MutableLiveData<>();

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
    private final MutableLiveData<List<CarCell>> carCellsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInProgressCellsLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isErrorAtCellsLoading = new MutableLiveData<>();

    public LiveData<List<CarCell>> getCarCellsMutableLiveData() {
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
    private final MutableLiveData<Car> carItemData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInProgressItemLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isErrorAtItemLoading = new MutableLiveData<>();

    public LiveData<Car> getCarItemData() {
        return carItemData;
    }

    public LiveData<Boolean> getIsInProgressItemLoading() {
        return isInProgressItemLoading;
    }

    public LiveData<Boolean> getIsErrorAtItemLoading() {
        return isErrorAtItemLoading;
    }

    /**
     * CarFavoritesFragment liveData
     */
    private final MutableLiveData<List<CarCell>> carFavoritesCellsData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInProgressFavoritesCellsLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isErrorAtFavoritesCellsLoading = new MutableLiveData<>();

    public LiveData<List<CarCell>> getCarFavoritesCellsData() {
        return carFavoritesCellsData;
    }

    public LiveData<Boolean> getIsErrorAtFavoritesCellsLoading() {
        return isErrorAtFavoritesCellsLoading;
    }

    public LiveData<Boolean> getIsInProgressFavoritesCellsLoading() {
        return isInProgressFavoritesCellsLoading;
    }

    /**
     * CarItemFavoritesFragment liveData
     */
    private final MutableLiveData<Car> carItemDataFavorites = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInProgressFavoriteItemLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isErrorAtFavoriteItemLoading = new MutableLiveData<>();

    public LiveData<Car> getCarItemDataFavorites() {
        return carItemDataFavorites;
    }

    public LiveData<Boolean> getIsInProgressFavoriteItemLoading() {
        return isInProgressFavoriteItemLoading;
    }

    public LiveData<Boolean> getIsErrorAtFavoriteItemLoading() {
        return isErrorAtFavoriteItemLoading;
    }

    private final ApiRepository apiRepository;
    private final DataBaseRepository dataBaseRepository;
    private final SchedulersProvider schedulersProvider;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Inject
    public AvitoParseViewModel(ApiRepository apiRepository, SchedulersProvider schedulersProvider, DataBaseRepository dataBaseRepository) {
        this.apiRepository = apiRepository;
        this.schedulersProvider = schedulersProvider;
        this.dataBaseRepository = dataBaseRepository;
    }

    public void loadBrandsData() {
        isErrorAtBrandListLoading.setValue(false);
        isInProgressBrandListLoading.setValue(true);
        compositeDisposable.add(apiRepository.getBrandList()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressBrandListLoading.setValue(false))
                .subscribe(brandListData::setValue,
                        e -> isErrorAtBrandListLoading.setValue(true)));
    }

    public void loadModelsData(String brand) {
        isErrorAtModelsListLoading.setValue(false);
        isInProgressModelsListLoading.setValue(true);
        compositeDisposable.add(apiRepository.getModelList(brand)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressModelsListLoading.setValue(false))
                .subscribe(modelsListData::setValue,
                        e -> isErrorAtModelsListLoading.setValue(true)));
    }

    /**
     * Метод для получения списка объявлений автомобилей на основе переданных параметров
     */
    public void loadCarCellsData(String brand, String model) {
        isErrorAtCellsLoading.setValue(false);
        isInProgressCellsLoading.setValue(true);
        compositeDisposable.add(apiRepository.getCarCells(brand, model)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressCellsLoading.setValue(false))
                .toObservable()
                .flatMap((Function<List<CarCell>, Observable<CarCell>>) carCells -> {
                    carCellsMutableLiveData.setValue(carCells);
                    return Observable.fromIterable(carCells);
                }).subscribe(cell -> {
                            checkIfCarCellExistInFavorites(cell);
                        },
                        e -> isErrorAtCellsLoading.setValue(true)));
//
//        Observable<CarCell> carCellObservable = apiRepository.getCarCells(brand, model)
//                .toObservable()
//                .flatMap((Function<List<CarCell>, Observable<CarCell>>) carCells -> {
//                    //carCellsMutableLiveData.setValue(carCells);
//                    return Observable.fromIterable(carCells);
//                });
//
//        Observable<CarCell> carFavObservable = dataBaseRepository.getAllFavoritesCarCells()
//                .toObservable()
//                .flatMap((Function<List<CarCell>, Observable<CarCell>>) carCells -> {
////                    carCellsMutableLiveData.setValue(carCells);
//                    return Observable.fromIterable(carCells);
//                });
//
//        compositeDisposable.add(carCellObservable.concatMap(new Function<CarCell, Observable<Boolean>>() {
//            @Override
//            public Observable<Boolean> apply(@NonNull CarCell cell) throws Exception {
//                return carFavObservable.contains(cell).toObservable();
//            }
//        }).zipWith(carCellObservable, new BiFunction<Boolean, CarCell, Object>() {
//            @NonNull
//            @Override
//            public Object apply(@NonNull Boolean aBoolean, @NonNull CarCell cell) throws Exception {
//                cell.setFavorite(aBoolean);
//                return carCellObservable;
//            }
//        })
//                .subscribeOn(schedulersProvider.io())
//                .observeOn(schedulersProvider.ui())
//                .doAfterTerminate(() -> isInProgressCellsLoading.setValue(false))
//                .subscribe(cell -> {
//                    System.out.println(cell);
//                }));

    }

    private void checkIfCarCellExistInFavorites(CarCell cell) {
        compositeDisposable.add(dataBaseRepository.checkIfCarCellExistInFavorites(cell)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe(isFavorite -> {
                    cell.setFavorite(isFavorite == 1);
                    carCellsMutableLiveData.setValue(carCellsMutableLiveData.getValue());
                }));
    }

    /**
     * Метод для получения полного объявления автомобиля на основе объекта CarCell
     *
     * @param carCell - выбранное объявление из списка, который был получен в loadCellsData(String params)
     */
    public void loadCarItemData(CarCell carCell) {
        String link = carCell.getLinkToItem();
        isInProgressItemLoading.setValue(true);
        isErrorAtItemLoading.setValue(false);
        compositeDisposable.add(apiRepository.getCar(link)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressItemLoading.setValue(false))
                .subscribe(carItemData::setValue,
                        e -> isErrorAtItemLoading.setValue(true)));
    }

    public void loadCarCellsFavorites() {
        compositeDisposable.add(dataBaseRepository.getAllFavoritesCarCells()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressCellsLoading.setValue(false))
                .subscribe(var -> {
                            carFavoritesCellsData.setValue(var);
                            if (var.isEmpty()) {
                                toggleCarFavoritesNoItems.setValue(true);
                            } else {
                                toggleCarFavoritesNoItems.setValue(false);
                            }
                        },
                        e -> isErrorAtFavoritesCellsLoading.setValue(true)));
    }

    public void loadCarItemFavoriteData(CarCell carCell) {
        String link = carCell.getLinkToItem();
        isInProgressItemLoading.setValue(true);
        isErrorAtItemLoading.setValue(false);
        compositeDisposable.add(apiRepository.getCar(link)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doAfterTerminate(() -> isInProgressFavoriteItemLoading.setValue(false))
                .subscribe(carItemDataFavorites::setValue,
                        e -> isErrorAtFavoriteItemLoading.setValue(true)));
    }

    public void insertOrDeleteIfExist(CarCell cell) {
        compositeDisposable.add(dataBaseRepository.insertOrDeleteIfExist(cell)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe(() -> {
                            toggleCarItemFavoritesButton.setValue(cell);
                            markerCarCellsFavorites.setValue(cell);
                            loadCarCellsFavorites();

                        },
                        e -> toggleToastUnsuccesfulDatabaseOperation.setValue(true)));
    }

    private MutableLiveData<Boolean> toggleToastUnsuccesfulDatabaseOperation;

    public LiveData<Boolean> getToggleToastUnsuccesfulDatabaseOperation() {
        return toggleToastUnsuccesfulDatabaseOperation;
    }

    //for toggle CarCells favorites markers
    private MutableLiveData<CarCell> markerCarCellsFavorites = new MutableLiveData<>();

    public LiveData<CarCell> getMarkerCarCellsFavorites() {
        return markerCarCellsFavorites;
    }

    public void setNewMarkerCarCellsFavorites() {
        markerCarCellsFavorites = new MutableLiveData<>();
    }

    //for toggle CarItemFavoriteButton
    private MutableLiveData<CarCell> toggleCarItemFavoritesButton = new MutableLiveData<>();

    public LiveData<CarCell> getToggleCarItemFavoritesButton() {
        return toggleCarItemFavoritesButton;
    }

    public void newToggleCarItemFavoritesButton() {
        toggleCarItemFavoritesButton = new MutableLiveData<>();
    }

    //for toggle NoCarFavoritesItems
    private MutableLiveData<Boolean> toggleCarFavoritesNoItems = new MutableLiveData<>();

    public LiveData<Boolean> getToggleCarFavoritesNoItems() {
        return toggleCarFavoritesNoItems;
    }

    public void newToggleCarFavoritesNoItems() {
        toggleCarFavoritesNoItems = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
