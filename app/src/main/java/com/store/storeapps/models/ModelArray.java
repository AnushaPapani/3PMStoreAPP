package com.store.storeapps.models;

import java.util.ArrayList;

/**
 * Created by shankar on 10/8/2016.
 */

public class ModelArray {
    private ArrayList<Movie> movies;
    private String OrderId;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
