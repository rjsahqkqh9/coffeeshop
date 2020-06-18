/*
 *  Create by platform eight on 2020. 6. 10.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;

    private String displayName;

    public LoggedInUser(String userId, String displayName ) {
        this.userId = userId;
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}