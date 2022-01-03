package com.company.stockclock;

public class ModelClass {

    private String imageName;
    private String categoryName;
    private String prevClose;
    private String stockName;

    public ModelClass(String imageName, String categoryName, String prevClose, String stockName) {
        this.imageName = imageName;
        this.categoryName = categoryName;
        this.prevClose = prevClose;
        this.stockName = stockName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getPrevClose(){ return prevClose;}

    public String getStockName(){return stockName;}
}
