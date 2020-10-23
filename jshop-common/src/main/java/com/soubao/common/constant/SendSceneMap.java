package com.soubao.common.constant;


import java.util.HashMap;
import java.util.Map;

public class SendSceneMap {

    private static final Map<String, String> sendScenes;

    static {
        sendScenes = new HashMap<>();
        sendScenes.put("1", "用户注册");
        sendScenes.put("2", "用户找回密码");
        sendScenes.put("3", "客户下单");
        sendScenes.put("4", "客户支付");
        sendScenes.put("5", "商家发货");
        sendScenes.put("6", "身份验证");
        sendScenes.put("7", "购买虚拟商品通知");
    }

    public static Map<String, String> getSendScenes() {
        return sendScenes;
    }

}
