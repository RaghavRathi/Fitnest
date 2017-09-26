package com.apkglobal.healthmanager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rahul on 6/14/2017.
 */

public class Methords {


        Context context;

        Methords(Context context) {
            this.context = context;
        }

        public void saveLoginDetails(String semail) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Email", semail);
            editor.commit();

        }

        public String getEmail() {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            return sharedPreferences.getString("Email", "");
        }

        public boolean checkLogin() {
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
            return  isEmailEmpty;
        }

        public void logout(){
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

        }
    }


