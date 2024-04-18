package com.example.inventorymanagement.models;

public class CategoryOfGridView {
    private int imgSource;
    private String categoryName;

    public CategoryOfGridView() {}

    public CategoryOfGridView(int imgSource, String categoryName) {
        this.imgSource = imgSource;
        this.categoryName = categoryName;
    }

    public void setImgSource(int imgSource) {
        this.imgSource = imgSource;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getImgSource() {
        return imgSource;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
