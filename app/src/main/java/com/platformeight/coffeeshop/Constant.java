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
    //public static final String local_name = "http://192.168.10.107/coffee/";
    public static final String folder = "shop/";
    //ServerHandle
    public static final String shop_login = server_name +folder + "shop_login_select.php";
    public static final String orderlist_shop = server_name +folder + "orderlist_shop.php";
    public static final String order_check = server_name +folder + "order_check_shop.php";
    public static final String shop_state_update = server_name +folder + "shop_state_update.php";


    public static final String token_update = server_name + "token_update.php";
    public static final String fcm_basic = server_name + "fcm.php";
    //json name
    public static final String REGISTER_SUCCESS = "members_register success";
    public static final String REGISTER_FAILURE = "members_register failure";
    public static final String ORDER_INSERT_SUCCESS = "order_insert success";
    public static final String ORDER_INSERT_FAILURE = "order_insert failure";
    public static final String ORDERLIST_SHOP = "orderlist_shop";
    public static final String MEMBER_SELECT = "member_select";

    //avtivity result code
    public static final int RESULT_LOGIN = 1000;
    public static final int RESULT_ORDER = 1001;
    public static final int RESULT_CART = 1002;
    //intent data code
    public static final String CART_CODE = "cart_code";
    public static final String SHOP_DATA = "ShopData";
    public static final String MENU = "menu";
    public static final String MYORDERS = "myorders";
    public static final String CART_ITEMS = "cart";
    public static final String CART_ITEM = "cart_item";
    public static final String LOGIN_STATE = "state";

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###");

}
