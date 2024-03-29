package com.example.mycompany.avitoparseapp.data.model;

/**
 * Объект-представление бренда
 */
public class Brand {
    private String name;
    private String brandModelsLink;

    public Brand(String name, String brandModelsLink) {
        this.name = name;
        this.brandModelsLink = brandModelsLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return brandModelsLink;
    }
}