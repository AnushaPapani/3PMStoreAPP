package com.store.storeapps.models;

import java.util.ArrayList;

/**
 * Created by shankar on 10/1/2016.
 */

public class ReviewOrderModel_Before_Login {

    private int Max_Quantity;
    private String P_ID;
    private String P_Image;
    private String P_Name;
    private String Cart_Prod_ID;
    private int P_Cost;
    private int P_Qty;
    private ArrayList<String> Attribute_Type;
    private ArrayList<String> Attribute_Value;

    public int getMax_Quantity() {
        return Max_Quantity;
    }

    public void setMax_Quantity(int max_Quantity) {
        Max_Quantity = max_Quantity;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getP_Image() {
        return P_Image;
    }

    public void setP_Image(String p_Image) {
        P_Image = p_Image;
    }

    public String getP_Name() {
        return P_Name;
    }

    public void setP_Name(String p_Name) {
        P_Name = p_Name;
    }

    public int getP_Cost() {
        return P_Cost;
    }

    public void setP_Cost(int p_Cost) {
        P_Cost = p_Cost;
    }

    public int getP_Qty() {
        return P_Qty;
    }

    public void setP_Qty(int p_Qty) {
        P_Qty = p_Qty;
    }

    public ArrayList<String> getAttribute_Type() {
        return Attribute_Type;
    }

    public void setAttribute_Type(ArrayList<String> attribute_Type) {
        Attribute_Type = attribute_Type;
    }

    public ArrayList<String> getAttribute_Value() {
        return Attribute_Value;
    }

    public void setAttribute_Value(ArrayList<String> attribute_Value) {
        Attribute_Value = attribute_Value;
    }

    public String getCart_Prod_ID() {
        return Cart_Prod_ID;
    }

    public void setCart_Prod_ID(String cart_Prod_ID) {
        Cart_Prod_ID = cart_Prod_ID;
    }
}
