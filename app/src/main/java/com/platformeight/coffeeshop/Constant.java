/*
 *  Create by platform eight on 2020. 6. 4.
 *  Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffeeshop;

import java.text.DecimalFormat;

public final class Constant {
    //서버주소 네이버클라우드 공인 ip searchcompany 폴더
    public static final String packageNmae = "com.platform8.coffeeshop";
    //TODO: 앱배포전 도메인 주소들 변경
    public static final String server_name = "http://210.89.189.186/coffee/";
    public static final String local_name = "http://192.168.10.107/coffee/";

    public static final String register_success = "members_register success";
    public static final String register_failure = "members_register failure";
    public static final String order_insert_success = "order_insert success";
    public static final String order_insert_failure = "order_insert failure";
    public static final String orderlist_user = "orderlist_user";
    public static final String member_select = "member_select";

    public static final int result_login = 1000;
    public static final int result_order = 1001;
    public static final int result_cart = 1002;

    public static final String cart_code = "cart_code";
    public static final String shopdata = "ShopData";
    public static final String menu = "menu";
    public static final String myorders = "myorders";
    public static final String cart_items = "cart";
    public static final String cart_item = "cart_item";
    public static final String login_state = "state";

    public static final DecimalFormat format = new DecimalFormat("###,###");

}
