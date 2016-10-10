package com.store.storeapps.models;

import java.util.ArrayList;

/**
 * Created by shankar on 10/8/2016.
 */

public class MyOrdersModel {
    private  ArrayList<ModelArray> ModelArray;
    private  ArrayList<String> orderIds;
    private  String success;
    private  String message;

    public ArrayList<ModelArray> getMovies() {
        return ModelArray;
    }

    public void setMovies(ArrayList<ModelArray> movies) {
        this.ModelArray = movies;
    }

    public ArrayList<String> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(ArrayList<String> orderIds) {
        this.orderIds = orderIds;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
