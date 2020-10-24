package org.soubao.shop.constant;

public enum OauthTag {

    WX("weixin"),
    ;
    private String tag; //第三方平台标识
    OauthTag(String tag) {
        this.tag = tag;
    }
    public String getag() {
        return tag;
    }
}
