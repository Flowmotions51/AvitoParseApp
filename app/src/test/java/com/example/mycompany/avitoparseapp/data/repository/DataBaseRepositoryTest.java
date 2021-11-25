package com.example.mycompany.avitoparseapp.data.repository;

import com.example.mycompany.avitoparseapp.data.model.CarCell;
import com.example.mycompany.avitoparseapp.database.CarCellDAO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

@RunWith(MockitoJUnitRunner.class)
public class DataBaseRepositoryTest {

    private DataBaseRepository dataBaseRepository;
    private CarCellDAO carCellDAO;

    @Before
    public void setUp() {
        carCellDAO = Mockito.mock(CarCellDAO.class);
        dataBaseRepository = new DataBaseRepository(carCellDAO);
    }

    @Test
    public void testGetAllFavoritesCarCells() {
        //Arrange
        List<CarCell> carCells = createCarCellsTestData();
        List<CarCell> anotherCarCells = anotherCarCellsTestData();
        Mockito.doReturn(Single.just(carCells)).when(carCellDAO).getAllRecordsFromDb();
        //Act
        TestObserver<List<CarCell>> testObserver = dataBaseRepository.getAllFavoritesCarCells().test();
        //Assert
        testObserver.assertResult(carCells);
        testObserver.assertValueAt(0, carCells);
        testObserver.assertNever(anotherCarCells);
        testObserver.dispose();
    }

    @Test
    public void testNegativeGetAllFavoritesCarCells() {
        //Arrange
        List<CarCell> carCells = createCarCellsTestData();
        Mockito.doReturn(Single.error(new RuntimeException("Error getting data from DB"))).when(carCellDAO).getAllRecordsFromDb();
        //Act
        TestObserver<List<CarCell>> testObserver = dataBaseRepository.getAllFavoritesCarCells().test();
        //Assert
        testObserver.assertFailureAndMessage(RuntimeException.class, "Error getting data from DB");
        testObserver.assertNever(carCells);
        testObserver.dispose();
    }

    @Test
    public void testCheckIfCarCellExistInFavorites() {
        //Arrange
        int result = 1;
        CarCell carCell = createCarCellsTestData().get(0);
        Mockito.doReturn(Single.just(result)).when(carCellDAO).selectCountByLinkItem(carCell.getLinkToItem());
        //Act
        TestObserver<Integer> testObserver = dataBaseRepository.checkIfCarCellExistInFavorites(carCell).test();
        //Assert
        testObserver.assertResult(result);
        testObserver.dispose();
    }

    @Test
    public void testErrorCheckIfCarCellExistInFavorites() {
        //Arrange
        int result = 1;
        CarCell carCell = createCarCellsTestData().get(0);
        Mockito.doReturn(Single.error(new RuntimeException("Error getting data from DB"))).when(carCellDAO).selectCountByLinkItem(carCell.getLinkToItem());
        //Act
        TestObserver<Integer> testObserver = dataBaseRepository.checkIfCarCellExistInFavorites(carCell).test();
        //Assert
        testObserver.assertFailureAndMessage(RuntimeException.class, "Error getting data from DB");
        testObserver.assertNever(result);
        testObserver.dispose();
    }

    @Test
    public void testInsertOrDeleteIfExist() {
        //Arrange
        int result = 1;
        CarCell carCell = createCarCellsTestData().get(0);
        Mockito.doReturn(Completable.complete()).when(carCellDAO).insertRecord(carCell);
        Mockito.doReturn(Single.just(result)).when(carCellDAO).selectCountByLinkItem(carCell.getLinkToItem());
        //Act
        TestObserver testObserver = dataBaseRepository.insertOrDeleteIfExist(carCell).test();
        //Assert
        testObserver.assertComplete();
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


}