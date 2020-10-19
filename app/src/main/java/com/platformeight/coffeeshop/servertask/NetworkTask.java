/*
 * Create by platform eight on 2019. 10. 22.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop.servertask;

import android.os.AsyncTask;

import org.json.JSONObject;


public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url;
    private JSONObject val;

    public NetworkTask(String url, JSONObject values) {

        this.url = url;
        this.val = values;
    }

    @Override
    protected String doInBackground(Void... String) {
        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, val); // 해당 URL로 부터 결과물을 얻어온다.
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
        //tv_outPut.setText(s);
    }
}