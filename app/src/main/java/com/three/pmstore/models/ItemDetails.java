package com.three.pmstore.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Shankar on 9/30/2016.
 */

public class ItemDetails implements Serializable {
    private ArrayList<String> images;
    private ArrayList<String> attrNames;
    private ArrayList<String> attrTypes;
    private ArrayList<String> attrValues;
    private ArrayList<String> attrColors;

    private boolean IsEnabled;
    private String Category;
    private String Category_Icon;
    private String Category_Icon_grey;
    private int P_Cost;
    private String P_CustomValidate;
    private String P_Date;
    private String P_Description;
    private String P_hfeatures;
    private String p_id;
    private String P_Information;
    private String P_Name;
    private int P_Qty;
    private String P_shortdesc;
    private String P_Video;
    private String Stock;
    private String StrikeMrp;


    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getAttrNames() {
        return attrNames;
    }

    public void setAttrNames(ArrayList<String> attrNames) {
        this.attrNames = attrNames;
    }

    public ArrayList<String> getAttrTypes() {
        return attrTypes;
    }

    public ArrayList<String> getAttrColors() {
        return attrColors;
    }

    public void setAttrColors(ArrayList<String> attrColors) {
        this.attrColors = attrColors;
    }

    public void setAttrTypes(ArrayList<String> attrTypes) {
        this.attrTypes = attrTypes;
    }

    public ArrayList<String> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(ArrayList<String> attrValues) {
        this.attrValues = attrValues;
    }

    public boolean isEnabled() {
        return IsEnabled;
    }

    public void setEnabled(boolean enabled) {
        IsEnabled = enabled;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCategory_Icon() {
        return Category_Icon;
    }

    public void setCategory_Icon(String category_Icon) {
        Category_Icon = category_Icon;
    }

    public int getP_Cost() {
        return P_Cost;
    }

    public void setP_Cost(int p_Cost) {
        P_Cost = p_Cost;
    }

    public String getP_CustomValidate() {
        return P_CustomValidate;
    }

    public void setP_CustomValidate(String p_CustomValidate) {
        P_CustomValidate = p_CustomValidate;
    }

    public String getP_Date() {
        return P_Date;
    }

    public void setP_Date(String p_Date) {
        P_Date = p_Date;
    }

    public String getP_Description() {
        return P_Description;
    }

    public void setP_Description(String p_Description) {
        P_Description = p_Description;
    }

    public String getP_hfeatures() {
        return P_hfeatures;
    }

    public void setP_hfeatures(String p_hfeatures) {
        P_hfeatures = p_hfeatures;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_Information() {
        return P_Information;
    }

    public void setP_Information(String p_Information) {
        P_Information = p_Information;
    }

    public String getP_Name() {
        return P_Name;
    }

    public void setP_Name(String p_Name) {
        P_Name = p_Name;
    }

    public int getP_Qty() {
        return P_Qty;
    }

    public void setP_Qty(int p_Qty) {
        P_Qty = p_Qty;
    }

    public String getP_shortdesc() {
        return P_shortdesc;
    }

    public void setP_shortdesc(String p_shortdesc) {
        P_shortdesc = p_shortdesc;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getStrikeMrp() {
        return StrikeMrp;
    }

    public void setStrikeMrp(String strikeMrp) {
        StrikeMrp = strikeMrp;
    }

    public String getP_Video() {
        return P_Video;
    }

    public void setP_Video(String p_Video) {
        P_Video = p_Video;
    }

    public String getCategory_Icon_grey() {
        return Category_Icon_grey;
    }

    public void setCategory_Icon_grey(String category_Icon_grey) {
        Category_Icon_grey = category_Icon_grey;
    }
}
