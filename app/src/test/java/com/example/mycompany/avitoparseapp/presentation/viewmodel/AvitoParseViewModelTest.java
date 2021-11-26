package com.example.mycompany.avitoparseapp.presentation.viewmodel;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.mycompany.avitoparseapp.api.SchedulersProvider;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.data.repository.ApiRepository;
import com.example.mycompany.avitoparseapp.data.repository.DataBaseRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class AvitoParseViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private ApiRepository apiRepository;
    @Mock
    private SchedulersProvider schedulersProvider;
    @Mock
    private DataBaseRepository dataBaseRepository;

    @Mock
    private Observer<Boolean> isInProgressCellsLoadingObserver;
    @Mock
    private Observer<Boolean> isErrorAtCellsLoadingObserver;
    @Mock
    private Observer<List<CarCell>> carCellsListObserver;

    @Mock
    private Observer<Boolean> isInProgressItemLoadingObserver;
    @Mock
    private Observer<Boolean> isErrorAtItemLoadingObserver;
    @Mock
    private Observer<Car> carItemDataObserver;

    @Mock
    private Observer<List<CarCell>> carFavoritesCellsListObserver;

    @Mock
    private Observer<Boolean> isErrorAtFavoritesCellsLoadingObserver;

    @Mock
    private Observer<Boolean> isInProgressFavoritesCellsLoadingObserver;

    @Mock
    private Observer<Boolean> toggleCarFavoritesNoItemsObserver;


    private AvitoParseViewModel avitoParseViewModel;

    @Before
    public void setUp() {
        when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());

        avitoParseViewModel = new AvitoParseViewModel(apiRepository, schedulersProvider, dataBaseRepository);

        avitoParseViewModel.getIsInProgressCellsLoading().observeForever(isInProgressCellsLoadingObserver);
        avitoParseViewModel.getIsErrorAtCellsLoading().observeForever(isErrorAtCellsLoadingObserver);
        avitoParseViewModel.getCarCellsMutableLiveData().observeForever(carCellsListObserver);

        avitoParseViewModel.getIsInProgressItemLoading().observeForever(isInProgressItemLoadingObserver);
        avitoParseViewModel.getIsErrorAtItemLoading().observeForever(isErrorAtItemLoadingObserver);
        avitoParseViewModel.getCarItemData().observeForever(carItemDataObserver);

        avitoParseViewModel.getCarFavoritesCellsData().observeForever(carFavoritesCellsListObserver);
        avitoParseViewModel.getIsInProgressFavoritesCellsLoading().observeForever(isInProgressFavoritesCellsLoadingObserver);
        avitoParseViewModel.getIsErrorAtFavoritesCellsLoading().observeForever(isErrorAtFavoritesCellsLoadingObserver);
        avitoParseViewModel.getToggleCarFavoritesNoItems().observeForever(toggleCarFavoritesNoItemsObserver);
    }

    @Test
    public void testLoadCarItemData() {
        when(apiRepository.getCar(anyString())).thenReturn(Single.just(createCarItemResponseData()));
        avitoParseViewModel.loadCarItemData(createCarCellsTestData().get(0));
        verify(isInProgressItemLoadingObserver).onChanged(true);
        verify(carItemDataObserver).onChanged(createCarItemResponseData());
        verify(isErrorAtItemLoadingObserver).onChanged(false);
    }

    @Test
    public void testLoadCarCellsFavorites() {
        List<CarCell> carCells = createCarCellsTestData();
        when(dataBaseRepository.getAllFavoritesCarCells()).thenReturn(Single.just(carCells));
        avitoParseViewModel.loadCarCellsFavorites();
        verify(carFavoritesCellsListObserver).onChanged(carCells);
        verify(isInProgressFavoritesCellsLoadingObserver).onChanged(false);
        verify(toggleCarFavoritesNoItemsObserver).onChanged(false);
    }

    @Test
    public void testLoadCarCellsFavoritesEmptyList() {
        List<CarCell> carCells = Collections.emptyList();
        when(dataBaseRepository.getAllFavoritesCarCells()).thenReturn(Single.just(carCells));
        avitoParseViewModel.loadCarCellsFavorites();
        verify(carFavoritesCellsListObserver).onChanged(carCells);
        verify(isInProgressFavoritesCellsLoadingObserver).onChanged(false);
        verify(toggleCarFavoritesNoItemsObserver).onChanged(true);
    }

    @Test
    public void testNegativeLoadCarCellsFavorites() {
        when(dataBaseRepository.getAllFavoritesCarCells()).thenReturn(Single.error(new RuntimeException("Ex")));
        avitoParseViewModel.loadCarCellsFavorites();
        verify(isErrorAtFavoritesCellsLoadingObserver).onChanged(true);
    }

    @Test
    public void testErrorLoadCarCellsFavorites() {
        when(dataBaseRepository.getAllFavoritesCarCells()).thenReturn(Single.error(new Throwable("Error")));
        avitoParseViewModel.loadCarCellsFavorites();
        verify(isErrorAtFavoritesCellsLoadingObserver).onChanged(true);
    }

    private Car createCarItemResponseData() {
        Car car = new Car("name", "link"
                , Arrays.asList("link", "link2", "link3"),
                "desc", "phone");
        return car;
    }

    private List<CarCell> createCarCellsTestData() {
        return new ArrayList<>(
                Arrays.asList(
                        new CarCell("https://image.com",
                                "https://image-first.com",
                                "https://link.com",
                                "CarName", "999999"),
                        new CarCell("https://image2.com",
                                "https://image-first2.com",
                                "https://link2.com",
                                "CarName2", "999999"),
                        new CarCell("https://image3.com",
                                "https://image-first3.com",
                                "https://link3.com",
                                "CarName3", "999999")
                )
        );
    }
}
