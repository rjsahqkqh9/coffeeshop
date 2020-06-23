/*
 * Create by platform eight on 2019. 10. 22.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop.servertask;

import android.util.Log;

import com.platformeight.coffeeshop.MemberData;
import com.platformeight.coffeeshop.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.platformeight.coffeeshop.Constant.MEMBER_SELECT;
import static com.platformeight.coffeeshop.Constant.fcm_basic;
import static com.platformeight.coffeeshop.Constant.order_check;
import static com.platformeight.coffeeshop.Constant.orderlist_shop;
import static com.platformeight.coffeeshop.Constant.ORDERLIST_SHOP;
import static com.platformeight.coffeeshop.Constant.REGISTER_FAILURE;
import static com.platformeight.coffeeshop.Constant.shop_login;
import static com.platformeight.coffeeshop.Constant.token_update;

public class ServerHandle {

    static final String TAG = "ServerHandle";

    String url;
    JSONObject json;

    public ServerHandle(){
        url = "";
        json = new JSONObject();
    }
    /*
     * 점포 관리용
     */
    public String login(String id,String pass) { //id, pass //커피집 용도
        url = shop_login;
        json = new JSONObject();
        try {
            json.put("id", id);
            json.put("pass", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return parserUser(result);
    }
    String parserUser(String str){
        String result = "";
        MyApplication.user = new MemberData();
        try{
            if (str==null) return "값없음";
            JSONObject order = new JSONObject(str);
            JSONArray index = order.getJSONArray(MEMBER_SELECT);
            for (int i = 0; i < index.length(); i++) {
                JSONObject tt = index.getJSONObject(i);
                result += "nm : " + tt.getString("name")+"\n";
                MyApplication.user.setNo(tt.getInt("no"));
                MyApplication.user.setName(tt.getString("name"));
                MyApplication.user.setPromotion(tt.getInt("promotion"));
                MyApplication.user.setState(tt.getInt("state"));
            }
            if(result.equals("")) return "값없음";
        }
        catch (JSONException e){
            Log.d(TAG, "parserUser: "+str);
        }
        return result;
    }

    /*
    * 가게관련
     */

    public String setToken(int no, String table, String token){
        url = token_update;
        json = new JSONObject();
        try {
            json.put("no", no);
            json.put("table", table);
            json.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (result.contains(REGISTER_FAILURE)){
            Log.d(TAG, "register sql query: "+result);
        }else if (result.contains("failure")){
            Log.d(TAG, "register connection : "+result);
        }
        return result;
    }
    /*
     * 주문 처리
     */

    public String getOrderList(int shop_no, int state, String check) {
        url = orderlist_shop;
        json = new JSONObject();
        try {
            json.put("shop_no", shop_no);
            json.put("state", state);
            json.put("check", check);
            //Log.d(TAG, "getShopList: mapx long"+latLng.longitude+" mapy lat" + latLng.latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "orderlist connection error : "+result);
        if (result.contains("failure")){
            Log.d(TAG, "orderlist connection error : "+result);
        }
        JSONObject order = null;
        try {
            order = new JSONObject(result);
            return order.getJSONArray(ORDERLIST_SHOP).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String setOrderCheck(int order_no, int state, boolean isCheck) {
        url = order_check;
        json = new JSONObject();
        try {
            json.put("no", order_no);
            json.put("state", state);
            json.put("isCheck", (isCheck)? "Y":"N");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (result.contains("failure")){
            Log.d(TAG, "order update connection error : "+result);
        }
        Log.d(TAG, "setOrderCheck: "+result);
        return result;
    }

    public boolean sendFCM(int no, String table, String testcode) {
        url = fcm_basic;
        json = new JSONObject();
        try {
            json.put("no", no);
            json.put("table", table);
            json.put("testcode", testcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "sendFCM: "+result);
        return !result.contains("failure");
    }
}
