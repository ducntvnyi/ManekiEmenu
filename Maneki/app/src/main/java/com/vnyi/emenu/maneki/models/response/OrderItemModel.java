package com.vnyi.emenu.maneki.models.response;

/**
 * Created by Hungnd on 11/8/17.
 */

public class OrderItemModel {

    private int id;
    private String imgPath;
    private String itemName;
    private int quantity;
    private float totalMoney;

    /**
     *
     * @param id
     * @param imgPath
     * @param itemName
     * @param quantity
     * @param totalMoney
     */
    public OrderItemModel(int id, String imgPath, String itemName, int quantity, float totalMoney) {
        this.id = id;
        this.imgPath = imgPath;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalMoney = totalMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "OrderItemModel{" +
                "id=" + id +
                ", imgPath='" + imgPath + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", totalMoney=" + totalMoney +
                '}';
    }
}
