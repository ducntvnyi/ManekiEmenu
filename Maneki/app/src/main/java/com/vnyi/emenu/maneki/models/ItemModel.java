package com.vnyi.emenu.maneki.models;

/**
 * Created by Hungnd on 11/8/17.
 */

public class ItemModel {

    private int id;
    private String itemName;
    private String price;

    public ItemModel() {

    }

    public ItemModel(int id, String itemName, String price) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
