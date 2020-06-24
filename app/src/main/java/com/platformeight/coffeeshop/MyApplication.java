/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import android.app.Application;
import android.util.Log;

import com.platformeight.coffeeshop.servertask.ServerHandle;

public class MyApplication extends Application {

    public static MemberData user;
    public static String device_token;
    public static boolean mLoginForm;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeData();
    }

    private void initializeData() {
        //설정 읽어오기 user 자동로그인
        user = new MemberData();
        mLoginForm = true;
        device_token = "";
        autologin();
    }
    public static String getDevice_token() {
        return device_token;
    }

    public static void setDevice_token(String device_token) {
        MyApplication.device_token = device_token;
    }

    private static final String AUTOLOGIN = "autologin";
    private void autologin() {
        //휴대폰내 로그인정보 읽어오기
        //SharedPreference.removeAllAttribute(this);
        if (SharedPreference.hasAttribute(this, "AUTO")) {
            String auto = SharedPreference.getAttribute(this, "AUTO");
            if (auto.equals("true")) {
                if (SharedPreference.hasAttribute(this, "ID")){
                    String id = SharedPreference.getAttribute(this, "ID");
                    Log.d(AUTOLOGIN, "member_id : "+id);
                    String pass = SharedPreference.getAttribute(this, "PASS");
                    new ServerHandle().login(id,pass);
                } else Log.d(AUTOLOGIN, "login data empty");
            }
        }
    }
    /*
    if (id.length()>0&&pass.length()>0){
                MemberHandle member = new MemberHandle(this);
                boolean result = member.login(id,pass);
                if (!result) Log.e(AUTOLOGIN, " failure" );
            }
     */

}
