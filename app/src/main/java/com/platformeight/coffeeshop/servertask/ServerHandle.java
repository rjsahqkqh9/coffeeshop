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

import static com.platformeight.coffeeshop.Constant.local_name;
import static com.platformeight.coffeeshop.Constant.member_select;
import static com.platformeight.coffeeshop.Constant.orderlist_user;
import static com.platformeight.coffeeshop.Constant.register_failure;
import static com.platformeight.coffeeshop.Constant.register_success;

public class ServerHandle {

    static final String TAG = "ServerHandle";

    String url;
    JSONObject json;

    public ServerHandle(){
        url = "";
        json = new JSONObject();
    }
    /*
     * 회원정보관련
     */
    public String login(String id,String pass) { //id, pass //커피집 용도
        url = local_name+"shop_login_select.php";
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
            JSONArray index = order.getJSONArray(member_select);
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

    /*
     * 주문 처리
     */

    public String getOrderList(int shop_no, int state) {
        url = local_name+"orderlist_shop.php";
        json = new JSONObject();
        try {
            json.put("shop_no", shop_no);
            json.put("state", state);
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
        if (result.contains("failure")){
            Log.d(TAG, "orderlist connection error : "+result);
        }
        JSONObject order = null;
        try {
            order = new JSONObject(result);
            return order.getJSONArray(orderlist_user).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String setOrderCheck(int order_no, int state, boolean isCheck) {
        url = local_name+"order_check_shop.php";
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
}
