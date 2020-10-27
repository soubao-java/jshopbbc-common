package org.soubao.common.constant;

import java.util.HashSet;
import java.util.Set;

public class OrderConstant {
    //order_status
    public static final int NOT_CONFIRMED = 0;//未确认
    public static final int CONFIRMED = 1;//确认
    public static final int DELIVERY = 2;//已收货
    public final static int CANCELLED = 3;//已取消
    public static final int COMMENTED = 4;//已评价
    public final static int INVALID = 5;//已作废
    //pay_status
    public static final int NOT_PAY = 0;//未支付
    public static final int PAYED = 1;//已支付
    public static final int PRE_PAY = 2;//付订金
    public static final int REFUND = 3;//退款

    public final static int CUSTOMER = 2;//前台用户身份
    public final static int SELLER = 1;//商家身份
    public final static int ADMIN = 1;//管理员身份

    private static final Set<String> thirdPayCode;

    static {
        thirdPayCode = new HashSet<>();
        thirdPayCode.add("weixin");
        thirdPayCode.add("miniAppPay");
        thirdPayCode.add("appWeixinPay");
    }

    public static Set<String> getThirdPayCode() {
        return thirdPayCode;
    }
}
