package com.store.storeapps.utility;

/**
 * Created by Shankar on 9/30/2016.
 */

public class Constants {

    public static final String DEVICE_TYPE = "android";
    public static int SPLASH_TIME_OUT = 2000;

    public static final int CONNECTION_TIMEOUT = 30000;
    /*for displaying logs*/

    // Log message On or Off
    public static final boolean logMessageOnOrOff = true;

    public static final String APP_PREF = "Store_Pref";
    public static final String CHARACTER_SET_ISO_8859_1 = "iso-8859-1";
    public static final String CHARACTER_SET_UTF_8 = "UTF-8";
    public static final String Name ="";
    public static final String Emailid ="";

    public String getName() {
        return Name;
    }

    public String getEmailid() {
        return Emailid;
    }

//    public void setName(String name) {
//        Name = name;
//    }
//
//    public void setEmailid(String emailid) {
//        Emailid = emailid;
//    }
}
