/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

public class MemberData {

    // 회원번호 members_login
    private int no;

    private String name;

    private String pass; //10~20자이내

    private int promotion;

    private int state;

    private int seat;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    //  모델 복사
    public void CopyData(MemberData param)
    {
        this.no = param.getNo();
        this.name = param.getName();
        this.pass = param.getPass();
        this.promotion = param.getPromotion();
        this.state = param.getState();
    }
}