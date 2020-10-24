package org.soubao.shop.constant;


import java.util.ArrayList;
import java.util.List;

public class ShopConstant {
    public final static String miniAppOauth = "miniapp";
    public final static String imageUpload = "/upload";
    public final static Integer cartMostNum = 20;
    public final static String goodsNoOriginalImage = "/public/images/icon_goods_thumb_empty_300.png";
    public final static Integer orderGoodsBarCodeStart = 16000001;
    public final static Integer orderGoodsBarCodeEnd = 16999999;
    public final static long imageMaxSize = 2097152;
    public final static List<String> imageType;
    public final static List<String> videoType;
    static {
        imageType = new ArrayList<>();
        videoType = new ArrayList<>();
        imageType.add("image/jpg");
        imageType.add("image/jpeg");
        imageType.add("image/png");
        imageType.add("image/gif");
        videoType.add("video/mp4");
        videoType.add("video/ogg");
        videoType.add("video/flv");
        videoType.add("video/avi");
        videoType.add("video/wmv");
        videoType.add("video/rmvb");
        videoType.add("video/mov");
    }


}