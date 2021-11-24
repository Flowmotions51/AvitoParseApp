package com.example.mycompany.avitoparseapp.data.model;

import junit.framework.TestCase;

import java.util.Collections;

public class CarCellTest extends TestCase {

    public void testCarEquals() {
        CarCell firstCarCell = new CarCell("link", "link", "link", "name", "price");
        CarCell secondCarCell = new CarCell("link", "link", "link", "name", "price");
        assertEquals(firstCarCell, secondCarCell);
    }

    public void testSetCarId() {
        int carIdToSet = 999;
        CarCell firstCarCell = new CarCell("link", "link", "link", "name", "price");
        firstCarCell.setCarId(carIdToSet);
        assertEquals(carIdToSet, firstCarCell.getCarId());
    }

    public void testSetFavorite() {
        boolean isFavorite = true;
        CarCell firstCarCell = new CarCell("link", "link", "link", "name", "price");
        firstCarCell.setFavorite(isFavorite);
        assertEquals(isFavorite, firstCarCell.isFavorite());
    }
}