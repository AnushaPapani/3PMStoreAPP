package com.three.pmstore.models;

import java.util.ArrayList;

/**
 * Created by shankar on 10/8/2016.
 */

public class MyOrdersModel {
    private  ArrayList<com.three.pmstore.models.ModelArray> ModelArray;
    private  ArrayList<String> orderIds;
    private  String success;
    private  String message;

    public ArrayList<com.three.pmstore.models.ModelArray> getMovies() {
        return ModelArray;
    }

    public void setMovies(ArrayList<com.three.pmstore.models.ModelArray> movies) {
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
