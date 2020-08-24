/*
 *  Create by platform eight on 2020. 6. 16.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.platformeight.coffeeshop.servertask.ServerHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.platformeight.coffeeshop.Constant.DECIMAL_FORMAT;
import static com.platformeight.coffeeshop.Constant.MYORDERS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OrderDetailFragment.OnListFragmentInteractionListener mListener;

    private JSONObject order;
    private int state;
    private char check;


    public OrderDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailFragment newInstance(String param1, String param2) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        TextView name = view.findViewById(R.id.order_detail_name);
        TextView address = view.findViewById(R.id.order_detail_address);
        TextView time = view.findViewById(R.id.order_detail_time);

        TextView total_quan = view.findViewById(R.id.order_detail_total_quantity);
        TextView total_price = view.findViewById(R.id.order_detail_total_price);

        TextView point = view.findViewById(R.id.order_detail_point);
        TextView price = view.findViewById(R.id.order_detail_price);

        Bundle bundle = getArguments();
        if (bundle != null && !bundle.isEmpty()) {
            String myorder = bundle.getString(MYORDERS);
            try {
                order = new JSONObject(myorder);
                name.setText(order.getString("name"));
                address.setText(order.getString("phone"));
                time.setText(order.getString("order_time"));

                /*
                ListView listView = (ListView) view.findViewById(R.id.order_detail_list);
                final MyorderdetailAdapter myAdapter = new MyorderdetailAdapter(getContext(),new JSONArray(order.getString("detail")));
                listView.setAdapter(myAdapter);
                //setListViewHeightBasedOnChildren(listView);
                */
                JSONArray ja = new JSONArray(order.getString("detail"));
                LinearLayout layout = view.findViewById(R.id.order_detail_group);
                for (int i=0;i<ja.length();i++)
                    layout.addView(setlist(ja,i));

                total_quan.setText(order.getString("order_amount"));
                total_price.setText(DECIMAL_FORMAT.format(order.getInt("order_price")));

                point.setText(DECIMAL_FORMAT.format(order.getInt("order_point")));
                price.setText(DECIMAL_FORMAT.format(order.getInt("total_price")));

                state = order.getInt("state");
                check = order.getString("isCheck").charAt(0);
                switch (state + check){
                    case 'Y': //완료내역 뒤로가기
                        Button btn_check = view.findViewById(R.id.order_detail_btn_check);
                        btn_check.setOnClickListener(v -> mListener.onListFragmentInteraction());
                        break;
                    case 1+'Y':
                        setButton(view, getString(R.string.order_detail_done_dialog),"주문완료", false);
                        break;
                    case 1+'N':
                        setButton(view, getString(R.string.order_detail_check_dialog),"주문확인", true);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return view;
    }
    private void setButton(View view, String message, String btn_text, boolean c){
        Button btn_check = view.findViewById(R.id.order_detail_btn_check);
        btn_check.setText(btn_text);
        btn_check.setOnClickListener(v -> new AlertDialog.Builder(getContext())
                .setTitle(btn_text)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    // 확인시 처리 로직
                    //Toast.makeText(getContext(), "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    try {
                        //TODO:: 확인다이얼로그 후 coffee_order isCheck 'Y' 및 FCM전송
                        String result = new ServerHandle().setOrderCheck(order.getInt("no"), (c)?1:0, true);
                        if (!result.contains("failure")){
                            new ServerHandle().sendFCM(order.getInt("member_no"), "coffee_members",(c)?"주문확인처리":"주문완료처리");
                        } else {
                            Toast.makeText(getContext(), "취소처리 알림\n고객측 주문변동사항을 감지하여 취소처리되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        mListener.onListFragmentInteraction();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton(android.R.string.no, (dialog, whichButton) -> {
                    // 취소시 처리 로직
                    Toast.makeText(getContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                })
                .show());
    }
    private View setlist(JSONArray items, int position){
        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
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
            name.setText(info);
            quan.setText(js.getString("amount"));
            price.setText(DECIMAL_FORMAT.format(js.getInt("price")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
            Log.d("", "setListViewHeightBasedOnChildren: "+ listItem.getMeasuredHeight());
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        Log.d("", "setListViewHeightBasedOnChildren: "+ params.height);
        params.height = (totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) );
        Log.d("", "setListViewHeightBasedOnChildren: "+ params.height);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderDetailFragment.OnListFragmentInteractionListener) {
            mListener = (OrderDetailFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction();
    }
}