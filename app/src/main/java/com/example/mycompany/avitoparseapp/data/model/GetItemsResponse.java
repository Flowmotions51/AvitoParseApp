package com.example.mycompany.avitoparseapp.data.model;

import java.util.List;

public class GetItemsResponse {
    private String message;
    private List<CarCell> carcells;

    public GetItemsResponse(String message, List<CarCell> carcells) {
        this.message = message;
        this.carcells = carcells;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CarCell> getCarCells() {
        return carcells;
    }

    public void setCarCells(List<CarCell> carcells) {
        this.carcells = carcells;
    }
}
