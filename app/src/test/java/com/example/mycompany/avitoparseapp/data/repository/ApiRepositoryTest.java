package com.example.mycompany.avitoparseapp.data.repository;

import android.accounts.NetworkErrorException;

import com.example.mycompany.avitoparseapp.api.ApiService;
import com.example.mycompany.avitoparseapp.data.model.Brand;
import com.example.mycompany.avitoparseapp.data.model.Car;
import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.data.model.Model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class ApiRepositoryTest {

    private ApiRepository apiRepository;
    private ApiService apiService;

    @Before
    public void setUp() {
        apiService = Mockito.mock(ApiService.class);
        apiRepository = new ApiRepository(apiService);
    }

    @Test
    public void testGetCarCells() {
        //Arrange
        List<CarCell> carCells = createCarCellsTestData();
        List<CarCell> neverCarCells = anotherCarCellsTestData();
        Mockito.doReturn(Single.just(carCells)).when(apiService).getResponseItemList(Mockito.anyString(), Mockito.anyString());
        //Act
        TestObserver<List<CarCell>> testObserver = apiRepository.getCarCells(Mockito.anyString(), Mockito.anyString()).test();
        //Assert
        testObserver.assertResult(carCells);
        testObserver.assertValueAt(0, carCells);
        testObserver.assertNever(neverCarCells);
        testObserver.dispose();
    }

    @Test
    public void testNegativeGetCarCells() {
        //Arrange
        List<CarCell> carCells = createCarCellsTestData();
        Mockito.doReturn(Single.error(new RuntimeException("Network error"))).when(apiService).getResponseItemList(Mockito.anyString(), Mockito.anyString());
        //Act
        TestObserver<List<CarCell>> testObserver = apiRepository.getCarCells(Mockito.anyString(), Mockito.anyString()).test();
        //Assert
        testObserver.assertFailureAndMessage(RuntimeException.class, "Network error");
        testObserver.assertNever(carCells);
        testObserver.dispose();
    }

    @Test
    public void testGetCar() {
        //Arrange
        Car car = getCar();
        Car anotherCar = anotherGetCar();
        Mockito.doReturn(Single.just(car)).when(apiService).getItem(Mockito.anyString());
        //Act
        TestObserver<Car> testObserver = apiRepository.getCar(Mockito.anyString()).test();
        //Assert
        testObserver.assertResult(car);
        testObserver.assertValueAt(0, car);
        testObserver.assertNever(anotherCar);
        testObserver.dispose();
    }

    @Test
    public void testNegativeGetCar() {
        //Arrange
        Car car = getCar();
        Mockito.doReturn(Single.error(new RuntimeException("Network error"))).when(apiService).getItem(Mockito.anyString());
        //Act
        TestObserver<Car> testObserver = apiRepository.getCar(Mockito.anyString()).test();
        //Assert
        testObserver.assertFailureAndMessage(RuntimeException.class, "Network error");
        testObserver.assertNever(car);
        testObserver.dispose();
    }

    @Test
    public void testGetBrandList() {
        //Arrange
        List<Brand> brandsList = getBrandList();
        List<Brand> anotherBrandsList = getAnotherBrandList();
        Mockito.doReturn(Single.just(brandsList)).when(apiService).getBrandList();
        //Act
        TestObserver<List<Brand>> testObserver = apiRepository.getBrandList().test();
        //Assert
        testObserver.assertResult(brandsList);
        testObserver.assertValueAt(0, brandsList);
        testObserver.assertNever(anotherBrandsList);
        testObserver.dispose();
    }

    @Test
    public void testNegativeGetBrandList() {
        //Arrange
        List<Brand> brandsList = getBrandList();
        Mockito.doReturn(Single.error(new RuntimeException("Network exception"))).when(apiService).getBrandList();
        //Act
        TestObserver<List<Brand>> testObserver = apiRepository.getBrandList().test();
        //Assert
        testObserver.assertFailureAndMessage(RuntimeException.class, "Network exception");
        testObserver.assertNever(brandsList);
        testObserver.dispose();
    }

    @Test
    public void testGetModelList() {
        //Arrange
        String brand = "Brand";
        List<Model> modelList = getModelList();
        List<Model> anotherModelList = getAnotherModelList();
        Mockito.doReturn(Single.just(modelList)).when(apiService).getModelsList(brand);
        //Act
        TestObserver<List<Model>> testObserver = apiRepository.getModelList(brand).test();
        //Assert
        testObserver.assertResult(modelList);
        testObserver.assertValueAt(0, modelList);
        testObserver.assertNever(anotherModelList);
        testObserver.dispose();
    }

    @Test
    public void testNegativeGetModelList() {
        //Arrange
        String brand = "Brand";
        List<Model> modelList = getModelList();
        Mockito.doReturn(Single.error(new RuntimeException("Network exception"))).when(apiService).getModelsList(brand);
        //Act
        TestObserver<List<Model>> testObserver = apiRepository.getModelList(brand).test();
        //Assert
        testObserver.assertFailureAndMessage(RuntimeException.class, "Network exception");
        testObserver.assertNever(modelList);
        testObserver.dispose();
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

    private List<CarCell> anotherCarCellsTestData() {
        return new ArrayList<>(
                Arrays.asList(
                        new CarCell("assertNever", "assertNever",
                                "assertNever", "assertNever", "assertNever"),
                        new CarCell("assertNever", "assertNever",
                                "assertNever", "assertNever", "assertNever"),
                        new CarCell("assertNever", "assertNever",
                                "assertNever", "assertNever", "assertNever")
                )
        );
    }

    private Car getCar() {
        return new Car("testCar", "testCar",
                Collections.singletonList("list"), "testCar","testCar");
    }

    private Car anotherGetCar() {
        return new Car("anotherGetCar", "anotherGetCar",
                Collections.singletonList("list-another"), "anotherGetCar","anotherGetCar");
    }

    private List<Brand> getBrandList() {
        return new ArrayList<>(Arrays.asList(
                new Brand("Brand1", "link1"),
                new Brand("Brand2", "link2"),
                new Brand("Brand3", "link3")
        ));
    }

    private List<Brand> getAnotherBrandList() {
        return new ArrayList<>(Arrays.asList(
                new Brand("AnotherBrand1", "Alink1"),
                new Brand("AnotherBrand2", "Alink2"),
                new Brand("AnotherBrand3", "Alink3")
        ));
    }

    private List<Model> getModelList() {
        return new ArrayList<>(Arrays.asList(
                new Model("NameModel1", "link1"),
                new Model("NameModel2", "link2"),
                new Model("NameModel3", "link3")
        ));
    }

    private List<Model> getAnotherModelList() {
        return new ArrayList<>(Arrays.asList(
                new Model("AnotherNameModel1", "Alink1"),
                new Model("AnotherNameModel2", "Alink2"),
                new Model("AnotherNameModel3", "Alink3")
        ));
    }
}