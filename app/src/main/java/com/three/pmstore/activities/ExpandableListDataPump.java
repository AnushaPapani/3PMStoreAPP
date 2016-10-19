package com.three.pmstore.activities;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump extends Activity {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> Credit = new ArrayList<String>();
        Credit.add("Beats sued for noise-cancelling tech");
        Credit.add("Wikipedia blocks US Congress edits");
        Credit.add("Google quizzed over deleted links");
        Credit.add("Nasa seeks aid with Earth-Mars links");
        Credit.add("The Good, the Bad and the Ugly");

        List<String> entertainment = new ArrayList<String>();
        entertainment.add("Goldfinch novel set for big screen");
        entertainment.add("Anderson stellar in Streetcar");
        entertainment.add("Ronstadt receives White House honour");
        entertainment.add("Toronto to open with The Judge");
        entertainment.add("British dancer return from Russia");

        List<String> science = new ArrayList<String>();
        science.add("Eggshell may act like sunblock");
        science.add("Brain hub predicts negative events");
        science.add("California hit by raging wildfires");
        science.add("Rosetta's comet seen in close-up");
        science.add("Secret of sandstone shapes revealed");
        
        expandableListDetail.put("ENTERTAINMENT NEWS", entertainment);
        expandableListDetail.put("Credi/Debit Card/Net Banking", Credit);
        expandableListDetail.put("Credisdaddassdadsd", science);
        
        
        
        return expandableListDetail;
    }
}
