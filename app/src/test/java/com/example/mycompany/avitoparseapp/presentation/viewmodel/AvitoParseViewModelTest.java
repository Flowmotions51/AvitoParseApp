package com.example.mycompany.avitoparseapp.presentation.viewmodel;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.mycompany.avitoparseapp.api.SchedulersProvider;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.data.model.GetItemsResponse;
import com.example.mycompany.avitoparseapp.data.repository.ApiRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

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
    private Observer<Boolean> isInProgressCellsLoadingObserver;
    @Mock
    private Observer<Boolean> isErrorAtCellsLoadingObserver;
    @Mock
    private Observer<GetItemsResponse> carCellsListObserver;

    private AvitoParseViewModel avitoParseViewModel;

    @Before
    public void setUp() {
        when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());

        avitoParseViewModel = new AvitoParseViewModel(apiRepository, schedulersProvider);

        avitoParseViewModel.getIsInProgressCellsLoading().observeForever(isInProgressCellsLoadingObserver);
        avitoParseViewModel.getIsErrorAtCellsLoading().observeForever(isErrorAtCellsLoadingObserver);
        avitoParseViewModel.getCarCellsMutableLiveData().observeForever(carCellsListObserver);
    }

    @Test
    public void testLoadCarCellsData() {
        when(apiRepository.getCarCells(anyString(), anyString())).thenReturn(Single.just(createTestData()));
        avitoParseViewModel.loadCarCellsData("bmw", "1-seriya", true);
        verify(isInProgressCellsLoadingObserver).onChanged(true);
        verify(carCellsListObserver).onChanged(createTestData());
        verify(isErrorAtCellsLoadingObserver).onChanged(false);
    }

    private GetItemsResponse createTestData() {
        GetItemsResponse testData = new GetItemsResponse("Ok", new ArrayList<>(
                Arrays.asList(
                        new CarCell("https://image.com",
                                "https://image-first.com",
                                "https://link.com",
                                "CarName"),
                        new CarCell("https://image2.com",
                                "https://image-first2.com",
                                "https://link2.com",
                                "CarName2"),
                        new CarCell("https://image3.com",
                                "https://image-first3.com",
                                "https://link3.com",
                                "CarName3")
                )
        ));
        return testData;
    }
}
