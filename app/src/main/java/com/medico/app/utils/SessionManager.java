package com.medico.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.medico.app.activity.MainActivity;
import com.medico.app.activity.StartActivity;
import com.medico.app.response.Address.AddressResult;
import com.medico.app.response.Banner.BannerResult;
import com.medico.app.response.Cartlist.CartResult;
import com.medico.app.response.OTp.OTPResponse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "medico";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String PROFILE_ID = "profile_id";
    public static final String TOKEN_ID = "token_id";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String FCM_TOKEN = "fcm_token";
    public static final String USER_Email = "user_Email";
    public static final String USER_Password = "user_Password";
    public static final String MOBILE = "mobile";
    public static final String ADDRESS_POSITION = "address_position";
    public static final String PAY_AMOUNT = "pay_amount";
    public static final String USER_ASKPERMISSION = "user_ask_permission";
    public static final String USER_LOCATION = "user_location";
    public static final String CITY = "city";
    public static final String PIN_CODE = "pin_code";
    public static final String LOCATION_ADDRESS = "location_address";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";


    // Constructor
    public SessionManager(Context context) {
        try {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
        }
    }

    /**
     * Create login session
     */


    public void createLoginSession(OTPResponse result) {
        //Log.e("inogin", new Gson().toJson(result));
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(TOKEN_ID, result.getData().getToken());
        editor.putString(NAME, result.getData().getName());
        editor.putString(USER_Email, result.getData().getEmail());
        editor.putString(MOBILE, result.getData().getMobile());
        Log.e("inogin", new Gson().toJson(result));
        // commit changes
        editor.commit();
    }

    public String getUserToken() {
        return pref.getString(TOKEN_ID, null);
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (this.isLoggedIn()) {
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        } else {
            Intent i = new Intent(_context, StartActivity.class);
            // Staring Login Activity
            //send data socialLogin page for
            i.putExtra("Splashpage", "fromSplash");
            _context.startActivity(i);
        }
    }

    public void saveFcmToken(String token) {
        editor.putString(FCM_TOKEN, token);
        editor.commit();
    }

    public String getFcmToken() {
        return pref.getString(FCM_TOKEN, null);
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(TOKEN_ID, pref.getString(TOKEN_ID, null));
        user.put(NAME, pref.getString(NAME, null));
        user.put(MOBILE, pref.getString(MOBILE, null));
        user.put(PROFILE_ID, pref.getString(PROFILE_ID, null));
        // return user
        return user;
    }


    public <T> void saveListInLocal(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void set(String key, String value) {
        if (pref != null) {
            SharedPreferences.Editor prefsEditor = pref.edit();
            prefsEditor.putString(key, value);
            prefsEditor.commit();
        }
    }


    public List<CartResult> getListFromLocal(String key) {
        if (pref != null) {
            Gson gson = new Gson();
            List<CartResult> drugsList;
            String string = pref.getString(key, null);
            Type type = new TypeToken<List<CartResult>>() {
            }.getType();
            drugsList = gson.fromJson(string, type);
            return drugsList;
        }
        return null;
    }

    public <T> void saveAddressInLocal(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        setAddress(key, json);
    }

    public void setAddress(String key, String value) {
        if (pref != null) {
            SharedPreferences.Editor prefsEditor = pref.edit();
            prefsEditor.putString(key, value);
            prefsEditor.commit();
        }
    }

    public List<AddressResult> getAddressFromLocal(String key) {
        if (pref != null) {
            Gson gson = new Gson();
            List<AddressResult> addressLists;
            String string = pref.getString(key, null);
            Type type = new TypeToken<List<AddressResult>>() {
            }.getType();
            addressLists = gson.fromJson(string, type);
            return addressLists;
        }
        return null;
    }

    public <T> void saveBannerLocal(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        setBanner(key, json);
    }

    public void setBanner(String key, String value) {
        if (pref != null) {
            SharedPreferences.Editor prefsEditor = pref.edit();
            prefsEditor.putString(key, value);
            prefsEditor.commit();
        }
    }

    public List<BannerResult> getBannerFromLocal(String key) {
        if (pref != null) {
            Gson gson = new Gson();
            List<BannerResult> bannerLists;
            String string = pref.getString(key, null);
            Type type = new TypeToken<List<BannerResult>>() {
            }.getType();
            bannerLists = gson.fromJson(string, type);
            return bannerLists;
        }
        return null;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        //Clear all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getUserId() {
        return pref.getString(PROFILE_ID, "");
    }

    public void setUserName(String user_name) {
        editor.putString(NAME, user_name);
        editor.apply();
    }

    public String getUserName() {
        return pref.getString(NAME, "");
    }

    public void setUserEmail(String c_name) {
        editor.putString(USER_Email, c_name);
        editor.apply();
    }

    public String getUserEmail() {
        return pref.getString(USER_Email, "null");
    }

    public void setUserPassword(String c_name) {
        editor.putString(USER_Password, c_name);
        editor.apply();
    }

    public String getUserPassword() {
        return pref.getString(USER_Password, "null");
    }


    public void saveMobile(String mobile) {
        editor.putString(MOBILE, mobile);
        editor.commit();
    }

    public String getMobile() {
        return pref.getString(MOBILE, null);
    }


    public void setAddressPosition(int position) {
        editor.putInt(ADDRESS_POSITION, position);
        editor.apply();
    }

    public int getAddressPosition() {
        return pref.getInt(ADDRESS_POSITION, 0);
    }

    public void setUserAskpermission() {
        editor.putString(USER_ASKPERMISSION, "no");
        editor.apply();
    }


    public String getUserAskpermission() {
        return pref.getString(USER_ASKPERMISSION, "null");
    }


    public void saveLocation(String city, String pin_code, String location_address, String latitude, String longitude) {
        editor.putString(CITY, city);
        editor.putString(PIN_CODE, pin_code);
        editor.putString(LOCATION_ADDRESS, location_address);
        editor.putString(LATITUDE, latitude);
        editor.putString(LONGITUDE, longitude);
        editor.commit();
    }

    public HashMap<String, String> getSaveLocation() {
        HashMap<String, String> location = new HashMap<String, String>();
        location.put(CITY, pref.getString(CITY, null));
        location.put(PIN_CODE, pref.getString(PIN_CODE, null));
        location.put(LOCATION_ADDRESS, pref.getString(LOCATION_ADDRESS, null));
        location.put(LATITUDE, pref.getString(LATITUDE, null));
        location.put(LONGITUDE, pref.getString(LONGITUDE, null));
        return location;
    }

   /* public void setUserLocation(String address) {
        editor.putString(USER_LOCATION, address);
        editor.apply();
    }

    public String getUserLocation() {
        return pref.getString(USER_LOCATION, "null");
    }*/
}