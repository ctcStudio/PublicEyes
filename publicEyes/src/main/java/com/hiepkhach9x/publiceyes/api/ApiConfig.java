package com.hiepkhach9x.publiceyes.api;

/**
 * Created by hungh on 5/17/2017.
 */

public class ApiConfig {

    public final static String METHOD_POST = "POST";
    public final static String METHOD_GET = "GET";
    public final static String IMAGE_URL = "http://acquyxenangdien.net";
    public final static String API = "api";
    public final static String API_LOGIN = "login";
    public final static String API_USER = "user";
    public final static String API_GET_USER = "getbyuser";
    public final static String API_PAYMENT = "payment";
    public final static String API_VIOLATION = "violation";
    public final static String API_FILE = "file";
    public final static String API_OPERATION = "operation";
    public final static String API_DATE = "date";
    public final static String API_VALUE = "getvalue";
    public final static String API_CATEGORY = "category";
    public final static String API_GET_ALL_CATEGORY = "getallcategory";

    public static String makeUrlImage(String path) {
        return IMAGE_URL + path;
    }

}
