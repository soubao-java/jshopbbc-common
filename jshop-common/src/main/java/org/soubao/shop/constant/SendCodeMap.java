package org.soubao.shop.constant;

import java.util.HashMap;
import java.util.Map;

public class SendCodeMap {
    private static final Map<String, String> orderSendCode;
    private static final Map<String, String> shippingSendCode;
    private static final Map<String, Map<String, String>> sendCode;
    private static final Map<String, String> successfully;
    private static final Map<String, String> complaint;
    private static final Map<String, String> userlog;

    static {
        userlog = new HashMap<>();
        complaint = new HashMap<>();
        successfully = new HashMap<>();
        orderSendCode = new HashMap<>();
        shippingSendCode = new HashMap<>();
        orderSendCode.put("{$order_no}", "订单编号");
        shippingSendCode.put("{$delivery_no}", "物流编号");
        shippingSendCode.put("{$delivery_name}", "物流名称");
        orderSendCode.putAll(shippingSendCode);
        successfully.put("{$order_no}", "订单编号");
        successfully.put("{$username}", "会员昵称");
        sendCode = new HashMap<>();
        sendCode.put("user_order", successfully);
        sendCode.put("deliver_goods_logistics", orderSendCode);
        complaint.put("{$complaint_no}", "投诉编号");
        complaint.put("{$complaint_out}", "处理结果");
        sendCode.put("customer_seller", complaint);
        userlog.put("{$username}", "会员昵称");
        sendCode.put("user_registration", userlog);
    }

    public static Map<String, String> getSendCodeMap(String code) {
        return sendCode.get(code);
    }





}
