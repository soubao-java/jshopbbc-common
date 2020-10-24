package org.soubao.shop.constant;


import java.util.HashSet;
import java.util.Set;

public class WxTradeType {

    private static final Set<String> tradeTypeSet;

    static {
        tradeTypeSet = new HashSet<>();
        tradeTypeSet.add("JSAPI");//JSAPI支付（或小程序支付）
        tradeTypeSet.add("NATIVE");//Native支付
        tradeTypeSet.add("APP");//app支付
        tradeTypeSet.add("MWEB");//-H5支付
    }

    public static Set<String> getSet() {
        return tradeTypeSet;
    }

}
