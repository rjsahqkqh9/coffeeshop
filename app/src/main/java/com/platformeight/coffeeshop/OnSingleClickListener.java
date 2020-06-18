/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public abstract class OnSingleClickListener implements View.OnClickListener {

    // 중복 클릭 방지 시간 설정
    private final long MIN_CLICK_INTERVAL = 700;

    private long mLastClickTime;

    public abstract void onSingleClick(View view);

    @Override
    public void onClick(View view) {

        /*
        // long currentClickTime = SystemClock.uptimeMillis();
        // long elapsedTime = currentClickTime - mLastClickTime;
        // mLastClickTime = currentClickTime;
        //
        // // 중복 클릭인 경우
        // if (elapsedTime <= MIN_CLICK_INTERVAL) {
        //     return;
        // }
        */
        /*무조껀 2초 뒤에 클릭을 한다.*/
        if (SystemClock.elapsedRealtime() - mLastClickTime < MIN_CLICK_INTERVAL) {
            Log.e("single click", "onClick: wait 0.7 second");
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        onSingleClick(view);
    }
}