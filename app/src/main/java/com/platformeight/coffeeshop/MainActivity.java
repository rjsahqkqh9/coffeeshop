/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.platformeight.coffeeshop.servertask.ServerHandle;
import com.platformeight.coffeeshop.ui.login.LoginActivity;

import org.json.JSONObject;

import static com.platformeight.coffeeshop.Constant.login_state;
import static com.platformeight.coffeeshop.Constant.myorders;
import static com.platformeight.coffeeshop.Constant.result_login;
import static com.platformeight.coffeeshop.MyApplication.user;

public class MainActivity extends AppCompatActivity implements OrderFragment.OnListFragmentInteractionListener, OrderDetailFragment.OnListFragmentInteractionListener, View.OnClickListener {

    private static final String TAG = "main";
    private ToolBarCustom toolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Context context = this;

    private FragmentManager fragmentManager;
    private OrderFragment orderFragment1;
    private OrderFragment orderFragment2;
    private FragmentTransaction transaction;

    private TextView tv_name;
    private Button btn_list1;
    private Button btn_list2;

    private int btn_state;
    private int flag_detail;
    private boolean mLoginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialView();
        initialData();
    }

    private void initialData() {
        if( user.getNo() < 1 ){
            mLoginForm = false;
            Intent intent = new Intent(context, LoginActivity.class);
            startActivityForResult(intent, result_login);
        }
        btn_list1.setOnClickListener(this);
        btn_list2.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();

    }
    private void initialView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBar = new ToolBarCustom(getSupportActionBar());
        toolBar.setTitleText((TextView) findViewById(R.id.toolbar_title), R.string.app_name_kor);
        tv_name = findViewById(R.id.main_name);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();
                Intent intent=null;
                switch (id){
                    case R.id.account:
                        Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.order_List:
                        Toast.makeText(context, title + ": 주문 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        //Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                        if (mLoginForm) { //로그인 및 회원가입
                            //intent = new Intent(context, LoginActivity.class);
                            //startActivityForResult(intent, result_login);
                        } else { //로그아웃시도
                            mLoginForm = true;
                            //changeLogin();
                            user = new MemberData();
                            finish();
                            //Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false;
            }
        });

        btn_state = 0;
        flag_detail = 0;
        btn_list1 = findViewById(R.id.myorder_btn_list1);
        btn_list2 = findViewById(R.id.myorder_btn_list2);
        btn_list1.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == result_login && resultCode == RESULT_OK) { //로그인 결과
            if (data.hasExtra(login_state)) {
                this.mLoginForm = !data.getBooleanExtra(login_state,false);
                Log.d(TAG, "onActivityResult: "+mLoginForm);
                if( user.getNo() < 1 || user == null ) return;
                tv_name.setText(user.getName());
                btn_list1.performClick();

                //TODO:: 정보수정 기기등록 페이지로 이동시킬것
                getToken("coffee_shops");
            }
        }
    }

    private void getToken(String table) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, new ServerHandle().setToken(user.getNo(),table,token));
                    }
                });
    }

    @Override
    public void onClick(View v) {
        btn_state = v.getId();
        switch (v.getId()){
            case R.id.myorder_btn_list1:
                //TODO:주문내역 받아오기
                //TODO: 내 주문내역 coffe_orders member_no
                orderFragment1 = new OrderFragment();
                //bundle.putString(menu, shop.getMenu());
                setFragment(orderFragment1, new ServerHandle().getOrderList(user.getNo(),1));
                btn_list1.setTextColor(Color.BLACK);
                btn_list2.setTextColor(Color.GRAY);
                flag_detail=0;
                break;
            case R.id.myorder_btn_list2:
                orderFragment2 = new OrderFragment();
                setFragment(orderFragment2, new ServerHandle().getOrderList(user.getNo(),0));
                btn_list1.setTextColor(Color.GRAY);
                btn_list2.setTextColor(Color.BLACK);
                flag_detail=0;
                break;
            default:
        }
    }
    private void setFragment(OrderFragment orderFragment, String myorder){
        Bundle bundle = new Bundle(1);
        bundle.putString(myorders, myorder);
        orderFragment.setArguments(bundle);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.myorder_list, orderFragment).commitAllowingStateLoss();
    }

    @Override
    public void onListFragmentInteraction(JSONObject item) {
        //상세정보출력
        //Intent intent = new Intent(this, OrderDetailActivity.class);
        //intent.putExtra(menu, item.toString());
        //intent.putExtra(cart_items, cart_list);
        //startActivity(intent);
        /*

         */
        OrderDetailFragment order = new OrderDetailFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(myorders, item.toString());
        transaction = fragmentManager.beginTransaction();
        order.setArguments(bundle);
        transaction.replace(R.id.myorder_list, order).commitAllowingStateLoss();
        flag_detail = 1;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //super.onBackPressed();
                //Toast.makeText(this, "서랍열기 ", Toast.LENGTH_SHORT).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (flag_detail == 1) {
            this.onClick(findViewById(btn_state));
            flag_detail = 0;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onListFragmentInteraction() {
        onBackPressed();
    }
}
