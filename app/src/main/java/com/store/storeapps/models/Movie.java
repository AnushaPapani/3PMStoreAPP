package com.store.storeapps.models;

/**
 * Created by shankar on 10/8/2016.
 */

public class Movie {
    private  String P_Name;
    private  String P_Qty;
    private  String P_Cost;
    private  String Attribute_Type;
    private  String Attribute_Value;
    private  String Shipping_Carrier;
    private  String Cart_Prod_ID;
    private  String Cart_ID;
    private  String U_ID;
    private  String P_ID;
    private  String P_Customattribute;
    private  String Order_Date;
    private  String Status;
    private  String Payment_Type;

    public String getP_Name() {
        return P_Name;
    }

    public void setP_Name(String p_Name) {
        P_Name = p_Name;
    }

    public String getP_Qty() {
        return P_Qty;
    }

    public void setP_Qty(String p_Qty) {
        P_Qty = p_Qty;
    }

    public String getP_Cost() {
        return P_Cost;
    }

    public void setP_Cost(String p_Cost) {
        P_Cost = p_Cost;
    }

    public String getAttribute_Type() {
        return Attribute_Type;
    }

    public void setAttribute_Type(String attribute_Type) {
        Attribute_Type = attribute_Type;
    }

    public String getAttribute_Value() {
        return Attribute_Value;
    }

    public void setAttribute_Value(String attribute_Value) {
        Attribute_Value = attribute_Value;
    }

    public String getShipping_Carrier() {
        return Shipping_Carrier;
    }

    public void setShipping_Carrier(String shipping_Carrier) {
        Shipping_Carrier = shipping_Carrier;
    }

    public String getCart_Prod_ID() {
        return Cart_Prod_ID;
    }

    public void setCart_Prod_ID(String cart_Prod_ID) {
        Cart_Prod_ID = cart_Prod_ID;
    }

    public String getCart_ID() {
        return Cart_ID;
    }

    public void setCart_ID(String cart_ID) {
        Cart_ID = cart_ID;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getP_Customattribute() {
        return P_Customattribute;
    }

    public void setP_Customattribute(String p_Customattribute) {
        P_Customattribute = p_Customattribute;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPayment_Type() {
        return Payment_Type;
    }

    public void setPayment_Type(String payment_Type) {
        Payment_Type = payment_Type;
    }
}
