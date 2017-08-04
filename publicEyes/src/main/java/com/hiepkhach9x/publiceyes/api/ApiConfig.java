package com.hiepkhach9x.publiceyes.api;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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


    public static final String API_COIN_SEND_ORDER = "send_order";

    public static final int COIN_SUCCESS = 1;

    public static String makeUrlImage(String path) {
        return IMAGE_URL + path;
    }

    public static final String NO_PHONE = "+84984921226";
    public static final String API_NUMBER = "1f52d883-20ba-4bfe-b584-9213ab7040a0";
    public static final String SECRET = "a13a927b-c9ea-46d5-98d9-1ffdcaa14b60";

    public static String getCoinAuth() {
        try {

            String auth = NO_PHONE + ":" + API_NUMBER;
            byte[] data = auth.trim().getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getUnixTime() {
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }

    public static String hashSha256(String nonce) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(nonce.getBytes("UTF-8"));
        return Base64.encodeToString(hash,Base64.NO_WRAP);
    }

    public static byte[] hashHmacSHA512(String message, String secret) {
        try {
            Mac sha_HMAC = Mac.getInstance("HmacSHA512");

            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
            sha_HMAC.init(secret_key);
            return sha_HMAC.doFinal(message.getBytes());
        }
        catch (Exception e){
            System.out.println("Error");
            return new byte[0];
        }
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for(byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString().toLowerCase();
    }
}
