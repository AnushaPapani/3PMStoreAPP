package com.store.storeapps.utility;

/**
 * Created by Shankar on 9/30/2016.
 */

public class ApiConstants {

    /*base URL*/
    public static final String BASE_URL = "http://8daysaweek.in/3productsaday/3PMstoreApp/3PMstore5189062/";
    public static final String URL_FORMS= "http://www.3pmstore.com/android/android_connect/";

    public static final String GET_ALL_PRODUCTS = BASE_URL + "get_all_products.php";
    public static final String INSERT_CHECK_PRODUCTS = BASE_URL + "insert_check_products.php";
    public static final String CHECKOUT_NEW = BASE_URL + "checkout_new.php";
    public static final String LOGIN = BASE_URL + "loginuser.php";
    public static final String REGISTER = BASE_URL + "create_user.php";
    public static final String PREVIOUS_PRODUCTS = BASE_URL + "previousproducts.php";

    public static final String MY_ORDERS = BASE_URL + "my_orders.php";

    public static final String FORMS_SUBMIT = URL_FORMS + "FormSubmit.php";
    public static final String CANCEL_RESONS = URL_FORMS + "get_CancelReasons.php";

    public static final String RETURN_REASONS =URL_FORMS +"get_returnreasons.php";
    public static final String RETURN_TYPE =URL_FORMS +"get_returntype.php";
}
