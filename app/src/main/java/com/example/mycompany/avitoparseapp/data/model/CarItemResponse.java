package com.example.mycompany.avitoparseapp.data.model;

public class CarItemResponse {
    private String message;
    private Car car;

    public CarItemResponse(String message, Car car) {
        this.message = message;
        this.car = car;
    }

    public String getMessage() {
        return message;
    }

    public Car getCar() {
        return car;
    }
}
