/*
 *  Create by platform eight on 2020. 6. 16.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.platformeight.coffeeshop.Constant.DECIMAL_FORMAT;

public class MyorderdetailAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    JSONArray items;

    public MyorderdetailAdapter(Context context, JSONArray data) {
        mContext = context;
        items = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return items.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return items.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.fragment_detail, null);

        TextView name = (TextView)view.findViewById(R.id.order_detail_menu_name);
        TextView quan = (TextView)view.findViewById(R.id.order_detail_menu_quantity);
        TextView price = (TextView)view.findViewById(R.id.order_detail_menu_price);
        try {
            JSONObject js = items.getJSONObject(position);
            String info = String.format("%s %s", js.getString("base"), js.getString("name"));
            JSONArray ja = new JSONArray(js.getString("opt"));
            JSONObject opt = ja.getJSONObject(0);
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            name.setText(info);
            quan.setText(js.getString("amount"));
            price.setText(DECIMAL_FORMAT.format(js.getInt("price")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
