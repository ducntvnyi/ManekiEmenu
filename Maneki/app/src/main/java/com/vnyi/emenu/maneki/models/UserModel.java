package com.vnyi.emenu.maneki.models;

import com.vnyi.emenu.maneki.models.response.UserOrder;

import java.util.List;

/**
 * Created by Hungnd on 11/10/17.
 */

public class UserModel {

    private List<UserOrder> userOrders;

    public List<UserOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userOrders=" + userOrders.toString() +
                '}';
    }
}
