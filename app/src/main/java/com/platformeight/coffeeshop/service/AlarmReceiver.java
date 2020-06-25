/*
 *  Create by platform eight on 2020. 6. 19.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    private static String TAG = "Alarm Receiver";
    private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(BOOT_ACTION))
        {
            Log.d(TAG, "BOOT_ACTION : alarm service ON");
            //context.startService(new Intent("com.google.firebase.MESSAGING_EVENT"));
            Intent service = new Intent(context, FCMService.class);
            service.setPackage("com.platformeight.coffeeshop.service");
            context.startService(service);

        }
    }

}
