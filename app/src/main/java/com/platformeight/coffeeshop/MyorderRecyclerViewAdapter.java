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

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyorderRecyclerViewAdapter extends RecyclerView.Adapter<MyorderRecyclerViewAdapter.ViewHolder> {

    private final List<JSONObject> mValues;
    private final OrderFragment.OnListFragmentInteractionListener mListner;

    public MyorderRecyclerViewAdapter(List<JSONObject> items, OrderFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListner = listener;
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
        holder.mItem = mValues.get(position);
        //holder.mMenuView.setText("menu "+position);
        //holder.mIdView.setText(mValues.get(position).id);
        //holder.mContentView.setText(mValues.get(position).content);
        try {
            holder.mOrderView.setText(holder.mItem.getString("name"));
            holder.mAddressView.setText(holder.mItem.getString("phone"));
            String name = new JSONArray(holder.mItem.getString("detail")).getJSONObject(0).getString("name");
            String info = String.format("%s 외 %d개\n총 결제금액 : %s원\n%s", name,
                    holder.mItem.getInt("order_amount"),
                    Constant.format.format(holder.mItem.getInt("total_price")),
                    holder.mItem.getString("order_time"));
            holder.mInfoView.setText(info);
            //holder.mInfoView.setText(holder.mItem.getString("info"));
            /*
            JSONArray ja = new JSONArray(holder.mItem.getString("info"));
            JSONObject js = ja.getJSONObject(0);
            for(Iterator<String> itr = js.keys(); itr.hasNext();){
                String str = itr.next();
                TextView tv = new TextView(holder.mView.getContext());
                tv.setText(String.format("- %s : ￦%s원", str, Constant.format.format(js.getInt(str))));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                //holder.mTextGroup.addView(tv);
            }
             */

            //holder.mMenuHotView.setText(String.format("- HOT : ￦%s원", format.format(js.getInt("hot"))));
            //holder.mMenuIceView.setText(String.format("- ICE : ￦%s원", format.format(js.getInt("ice"))));
            //holder.mMenuHotView.setText("HOT : " + js.getString("hot"));
            //holder.mMenuIceView.setText("ICE : " + js.getString("ice"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.mView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mListner.onListFragmentInteraction(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mValues==null) return 0;
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mOrderView;
        public final TextView mAddressView;
        public final TextView mInfoView;

        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mOrderView = (TextView) view.findViewById(R.id.order_title);
            mAddressView = (TextView) view.findViewById(R.id.order_address);
            mInfoView = (TextView) view.findViewById(R.id.order_info);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mOrderView.getText() + "'";
        }
    }
}