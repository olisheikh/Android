package com.example.inventorymanagement.models;

public class OptionsModel {
    private int optionsImgSource;
    private String optionsDescription;

    public OptionsModel(int optionsImgSource, String optionsDescription) {
        this.optionsImgSource = optionsImgSource;
        this.optionsDescription = optionsDescription;
    }

    public OptionsModel() {
    }

    public int getOptionsImgSource() {
        return optionsImgSource;
    }

    public void setOptionsImgSource(int optionsImgSource) {
        this.optionsImgSource = optionsImgSource;
    }

    public String getOptionsDescription() {
        return optionsDescription;
    }

    public void setOptionsDescription(String optionsDescription) {
        this.optionsDescription = optionsDescription;
    }
}
