package com.example.mycompany.avitoparseapp.data.model;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetItemsResponse that = (GetItemsResponse) o;
        return Objects.equals(message, that.message) && Objects.equals(carcells, that.carcells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, carcells);
    }

}
