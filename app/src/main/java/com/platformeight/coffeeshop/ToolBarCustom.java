/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import android.graphics.Color;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class ToolBarCustom {

    ActionBar actionBar;
    TextView textTitle;

    ToolBarCustom(ActionBar action){
        /*
        setActionBar(action);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar_custom);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back_black);
        */
        this.actionBar = action;
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
    }

    public void setActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    public void setTitleText(TextView view) { this.textTitle = view; }
    public void setTitle(int titleId){ this.textTitle.setText(titleId); }
    public void setTitle(String title){ this.textTitle.setText(title); }
    public void setTitleColor(String colorcode){ this.textTitle.setTextColor(Color.parseColor(colorcode)); }
    public void setTitleText(TextView view, int titleId){
        this.textTitle = view;
        this.textTitle.setText(titleId);
    }
    public void setTitleText(TextView view, String title){
        this.textTitle = view;
        this.textTitle.setText(title);
    }

}
