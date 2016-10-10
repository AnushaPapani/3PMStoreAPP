package com.store.storeapps.utility;

/**
 * Author :Raj Amal
 * Email  :raj.amalw@learn2crack.com
 * Website:www.learn2crack.com
 **/

import android.content.Context;



import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserFunctions {

    private JSONParser jsonParser;

    //URL of the PHP API
  // private static String loginURL = "http://3pmstore.com/dev_nikhil/sample_connect/";
   // private static String registerURL = "http://3pmstore.com/dev_nikhil/sample_connect/";
    private static String forpassURL = "http://www.3pmstore.com/android/android_connect/forgotpassword.php";
    private static String contactusURL = "http://www.3pmstore.com/android/android_connect/contactus.php";

   // private static String chgpassURL = "http://3pmstore.com/dev_nikhil/sample_connect/";

    private static String forpass_tag = "forpass";
    private static String contactus_tag = "contactus";

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * Function to Login
     **/

    /**
     * Function to change password
     **/

    /**
     * Function to reset the password
     **/

    public JSONObject forPass(String forgotpassword){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl(forpassURL, params);
        return json;
    }



    public JSONObject contactUs(String name, String email, String subject, String message){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag1", contactus_tag));
        //params.add(new BasicNameValuePair("contactus", contactus));
       params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("subject", subject));
       params.add(new BasicNameValuePair("message", message));
        JSONObject json = jsonParser.getJSONFromUrl(contactusURL, params);
        return json;
    }


    /**
     * Function to  Register
     **/




}

