package com.example.mycompany.avitoparseapp.data.model;

import junit.framework.TestCase;

import java.util.Collections;

public class CarTest extends TestCase {

    public void testCarEquals() {
        Car firstCar = new Car("Name", "Link",
                Collections.singletonList("link"), "Desc", "Phone");
        Car secondCar = new Car("Name", "Link",
                Collections.singletonList("link"), "Desc", "Phone");
        assertEquals(firstCar, secondCar);
    }
}