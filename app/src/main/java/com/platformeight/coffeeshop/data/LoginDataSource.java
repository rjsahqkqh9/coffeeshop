/*
 *  Create by platform eight on 2020. 6. 10.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop.data;

import android.util.Log;

import com.platformeight.coffeeshop.data.model.LoggedInUser;
import com.platformeight.coffeeshop.servertask.ServerHandle;

import java.io.IOException;

import static com.platformeight.coffeeshop.MyApplication.user;
import static com.platformeight.coffeeshop.MyApplication.user;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication 로그인 인증 처리
            String result = new ServerHandle().login(username,password);
            Log.d("LoginDataSource", "login: "+result);
            if (user.getNo()<1)  throw new Exception("No such as");
            LoggedInUser fakeUser = new LoggedInUser(java.util.UUID.randomUUID().toString(), user.getName());
            /*
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "fakeuser");
             */
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication 로그아웃
    }
}