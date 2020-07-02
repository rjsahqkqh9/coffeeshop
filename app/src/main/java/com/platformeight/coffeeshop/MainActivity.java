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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

import static com.platformeight.coffeeshop.Constant.LOGIN_STATE;
import static com.platformeight.coffeeshop.Constant.MYORDERS;
import static com.platformeight.coffeeshop.Constant.RESULT_LOGIN;
import static com.platformeight.coffeeshop.MyApplication.mLoginForm;
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
    private OrderFragment orderFragment3;
    private FragmentTransaction transaction;

    private TextView tv_name;
    private Button btn_list1;
    private Button btn_list2;
    private Button btn_list3;
    private ToggleButton tg_state;

    private int btn_state;
    private int flag_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialView();
        initialData();
    }

    private void initialData() {
        btn_state = 0;
        flag_detail = 0;
        if( user.getNo() < 1 ){
            mLoginForm = true;
            Intent intent = new Intent(context, LoginActivity.class);
            startActivityForResult(intent, RESULT_LOGIN);
        } else {
            mLoginForm = false;
        }
        changeLogin();
        btn_list1.setOnClickListener(this);
        btn_list2.setOnClickListener(this);
        btn_list3.setOnClickListener(this);
        tg_state.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d(TAG, "initialData: "+isChecked);
            int state = 0;
            if (isChecked) state = 1;
            Log.d(TAG, "initialData: "+ new ServerHandle().setState(user.getNo(),state));
        });
        fragmentManager = getSupportFragmentManager();
        btn_list1.performClick();
    }
    private void initialView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBar = new ToolBarCustom(getSupportActionBar());
        toolBar.setTitleText((TextView) findViewById(R.id.toolbar_title), R.string.app_name_kor);
        tv_name = findViewById(R.id.main_name);
        tg_state = findViewById(R.id.main_toggle_state);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            //menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            int id = menuItem.getItemId();
            String title = menuItem.getTitle().toString();
            Intent intent=null;
            switch (id){
                case R.id.account:
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "initialData: "+new ServerHandle().setState(user.getNo(),0));
                    break;
                case R.id.bank:
                    Toast.makeText(context, title + ": 정산 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.setting:
                    //Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                    //TODO:: 정보수정 기기등록 페이지로 이동시킬것
                    getToken("coffee_shops");
                    break;
                case R.id.logout:
                    //Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                    if (mLoginForm) { //로그인 및 회원가입
                        intent = new Intent(context, LoginActivity.class);
                        startActivityForResult(intent, RESULT_LOGIN);
                    } else { //로그아웃시도
                        mLoginForm = true;
                        changeLogin();
                        user = new MemberData();
                        //finish();
                        //Toast.makeText(context, "로그아웃", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        });

        btn_list1 = findViewById(R.id.myorder_btn_list1);
        btn_list2 = findViewById(R.id.myorder_btn_list2);
        btn_list3 = findViewById(R.id.myorder_btn_list3);
        btn_list1.clearFocus();
    }
    public void changeLogin(){
        if (mLoginForm){ //로그인되었는지 확인
            mLoginForm = true;
            navigationView.getMenu().findItem(R.id.logout).setTitle(getString(R.string.menu_login));
            navigationView.getMenu().findItem(R.id.account).setEnabled(false);
            navigationView.getMenu().findItem(R.id.bank).setEnabled(false);
            tv_name.setText(getString(R.string.nav_header_title));
            //nav_name.setText(getString(R.string.nav_header_title));
            //nav_point.setText(getString(R.string.nav_header_subtitle));
            tg_state.setEnabled(false);
        } else { //로그인성공
            mLoginForm = false;
            navigationView.getMenu().findItem(R.id.logout).setTitle(getString(R.string.menu_logout));
            navigationView.getMenu().findItem(R.id.account).setEnabled(true);
            navigationView.getMenu().findItem(R.id.bank).setEnabled(true);
            tv_name.setText(user.getName()+"님 환영합니다.");
            //nav_name.setText(user.getName());
            //nav_point.setText(""+user.getPoint());
            tg_state.setEnabled(true);
            tg_state.setChecked(user.getState() == 1);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOGIN && resultCode == RESULT_OK) { //로그인 결과
            if (data.hasExtra(LOGIN_STATE)) {
                mLoginForm = !data.getBooleanExtra(LOGIN_STATE,false);
                Log.d(TAG, "onActivityResult: "+mLoginForm);
                //if( user.getNo() < 1 || user == null ) return;
                //tv_name.setText(user.getName());
                changeLogin();
                btn_list1.performClick();
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
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, new ServerHandle().setToken(user.getNo(),table,token));
                        //context.startService(new Intent("com.google.firebase.MESSAGING_EVENT"));
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
                setFragment(orderFragment1, new ServerHandle().getOrderList(user.getNo(),1, "N"));
                btn_list1.setTextColor(Color.BLACK);
                btn_list2.setTextColor(Color.GRAY);
                btn_list3.setTextColor(Color.GRAY);
                flag_detail=0;
                break;
            case R.id.myorder_btn_list2:
                orderFragment2 = new OrderFragment();
                setFragment(orderFragment2, new ServerHandle().getOrderList(user.getNo(),1, "Y"));
                btn_list1.setTextColor(Color.GRAY);
                btn_list2.setTextColor(Color.BLACK);
                btn_list3.setTextColor(Color.GRAY);
                flag_detail=0;
                break;
            case R.id.myorder_btn_list3:
                orderFragment3 = new OrderFragment();
                setFragment(orderFragment3, new ServerHandle().getOrderList(user.getNo(),0, "Y"));
                btn_list1.setTextColor(Color.GRAY);
                btn_list2.setTextColor(Color.GRAY);
                btn_list3.setTextColor(Color.BLACK);
                flag_detail=0;
                break;
            default:
        }
    }
    private void setFragment(OrderFragment orderFragment, String myorder){
        Bundle bundle = new Bundle(1);
        bundle.putString(MYORDERS, myorder);
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
        bundle.putString(MYORDERS, item.toString());
        transaction = fragmentManager.beginTransaction();
        order.setArguments(bundle);
        transaction.replace(R.id.myorder_list, order).commitAllowingStateLoss();
        flag_detail = 1;
    }
    @Override
    public void onListFragmentInteraction(int index) {
        //갱신
        this.onClick(findViewById(btn_state));
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
