package com.example.mayank.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.mayank.Activity.FirstPage;


public class SessionManager {
    SharedPreferences sp;
    Context context;
    SharedPreferences.Editor editor;


    private final String PREF_FILE_NAME="shopping";
    private final int PRIVATE_MODE = 0;


    private final String KEY_NAME="key_session_name";
    private final String KEY_EMAIL="key_session_email";
    private final String KEY_PHNO="key_session_phno";
    private final String KEY_IF_LOGGED_IN="key_session_if_logged_in" ;
    public SessionManager(Context context) {
        this.context=context;
        sp= context.getSharedPreferences(PREF_FILE_NAME,PRIVATE_MODE );
        editor= sp.edit();
    }
    public boolean checkSession(){
        if(sp.contains(KEY_IF_LOGGED_IN)){
            return true;
        }
        else{
            return false;
        }
    }
    public void createSession(String email){
        editor.putString(KEY_EMAIL,email);
        editor.putBoolean(KEY_IF_LOGGED_IN,true);
        editor.commit();
    }


    public String getSessionDetails(String key){
        String value = sp.getString(key,null);
        return value;


    }

    public void logoutSession(){
        editor.clear();
        editor.commit();


    }
}
