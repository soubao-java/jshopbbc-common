package org.soubao.common.constant;

import java.util.HashMap;
import java.util.Map;

public class SendCodeMap {
    private static final Map<String, String> orderSendCode;
    private static final Map<String, String> shippingSendCode;
    private static final Map<String, Map<String, String>> sendCode;
    private static final Map<String, String> successfully;
    private static final Map<String, String> complaint;
    private static final Map<String, String> userlog;
    private static final Map<String, String> trialGoods;
    private static final Map<String, String> coupon;
    private static final Map<String, String> flashSale;
    private static final Map<String, String> groupBuy;
    private static final Map<String, String> preSell;
    private static final Map<String, String> promGoods;
    private static final Map<String, String> teamActivity;
    private static final Map<String, String> comment;
    private static final Map<String, String> promOrder;
    private static final Map<String, String> refundOrder;
    private static final Map<String, String> couponOut;

    static {
        sendCode = new HashMap<>();
        userlog = new HashMap<>();
        complaint = new HashMap<>();
        successfully = new HashMap<>();
        orderSendCode = new HashMap<>();
        shippingSendCode = new HashMap<>();
        trialGoods =new HashMap<>();
        coupon =new HashMap<>();
        flashSale =new HashMap<>();
        groupBuy =new HashMap<>();
        preSell =new HashMap<>();
        promGoods =new HashMap<>();
        teamActivity =new HashMap<>();
        comment =new HashMap<>();
        promOrder=new HashMap<>();
        refundOrder=new HashMap<>();
        couponOut=new HashMap<>();
        couponOut.put("{$coupon_name}", "优惠卷名称");
        refundOrder.put("{$order_sn}", "退款订单");
        refundOrder.put("{$username}", "会员昵称");
        promOrder.put("{$prom_order_name}","订单促销名称");
        comment.put("{$order_sn}", "评价订单");
        teamActivity.put("{$team_name}", "拼团标题");
        promGoods.put("{$prom_goods_name}", "促销活动标题");
        preSell.put("{$pre_sell_name}", "预售标题");
        groupBuy.put("{$group_buy_name}", "团购标题");
        flashSale.put("{$flash_sale_name}", "抢购标题");
        coupon.put("{$coupon_name}", "优惠卷名称");
        orderSendCode.put("{$order_no}", "订单编号");
        shippingSendCode.put("{$delivery_no}", "物流编号");
        shippingSendCode.put("{$delivery_name}", "物流名称");
        orderSendCode.putAll(shippingSendCode);
        successfully.put("{$order_no}", "订单编号");
        successfully.put("{$username}", "会员昵称");
        complaint.put("{$complaint_no}", "投诉编号");
        complaint.put("{$complaint_out}", "处理结果");
        userlog.put("{$username}", "会员昵称");
        trialGoods.put("{$user_name}","会员昵称");
        trialGoods.put("{$goods_name}","商品名称");
        sendCode.put("user_registration", userlog);
        sendCode.put("customer_seller", complaint);
        sendCode.put("user_order", successfully);
        sendCode.put("deliver_goods_logistics", orderSendCode);
        sendCode.put("trial_goods_win",trialGoods);
        sendCode.put("coupon_get_notice",coupon);
        sendCode.put("coupon_will_expire_notice",coupon);
        sendCode.put("flash_sale_activity",flashSale);
        sendCode.put("group_buy_activity",groupBuy);
        sendCode.put("pre_sell_activity",preSell);
        sendCode.put("prom_goods_activity",promGoods);
        sendCode.put("team_activity",teamActivity);
        sendCode.put("evaluate_logistics",comment);
        sendCode.put("prom_order_activity",promOrder);
        sendCode.put("refund_logistics",refundOrder);
        sendCode.put("coupon_use_notice",couponOut);
    }

    public static Map<String, String> getSendCodeMap(String code) {
        return sendCode.get(code);
    }

}
