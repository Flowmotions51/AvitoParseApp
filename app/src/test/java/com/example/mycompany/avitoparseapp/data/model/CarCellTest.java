package com.example.mycompany.avitoparseapp.data.model;

import junit.framework.TestCase;

public class CarCellTest extends TestCase {

    public void testCarEquals() {
        CarCell firstCarCell = new CarCell("link", "link", "link", "name", "price");
        CarCell secondCarCell = new CarCell("link", "link", "link", "name", "price");
        assertEquals(firstCarCell, secondCarCell);
    }

    public void testSetFavorite() {
        boolean isFavorite = true;
        CarCell firstCarCell = new CarCell("link", "link", "link", "name", "price");
        firstCarCell.setFavorite(isFavorite);
        assertEquals(isFavorite, firstCarCell.isFavorite());
    }
}