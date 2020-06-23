/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.platformeight.coffeeshop.Constant.DECIMAL_FORMAT;

/**
 * {@link RecyclerView.Adapter} that can display a {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyorderdetailRecyclerViewAdapter extends RecyclerView.Adapter<MyorderdetailRecyclerViewAdapter.ViewHolder> {

    private final JSONArray items;

    public MyorderdetailRecyclerViewAdapter(JSONArray items) {
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new ViewHolder(view);
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);
        //holder.mMenuView.setText("menu "+position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        try {
            holder.mItem = items.getJSONObject(position);
            String info = String.format("%s %s", holder.mItem.getString("base"), holder.mItem.getString("name"));
            JSONArray ja = new JSONArray(holder.mItem.getString("opt"));
            JSONObject opt = ja.getJSONObject(0);
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                info += String.format("\n   - "+str);
            }
            holder.name.setText(info);
            holder.quan.setText(holder.mItem.getString("amount"));
            holder.price.setText(DECIMAL_FORMAT.format(holder.mItem.getInt("price")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (items==null) return 0;
        return items.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView name;
        TextView quan;
        TextView price;
        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            name = (TextView)view.findViewById(R.id.order_detail_menu_name);
            quan = (TextView)view.findViewById(R.id.order_detail_menu_quantity);
            price = (TextView)view.findViewById(R.id.order_detail_menu_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}