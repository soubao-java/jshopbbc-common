package org.soubao.shop.utils;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import java.util.HashMap;
import java.util.Map;

public final class GoodsImageUtils {
    private static Map<String, String> waterPositionMap;
    static{
        waterPositionMap = new HashMap<>();
        waterPositionMap.put("1", "nw");      //标识左上角水印
        waterPositionMap.put("2", "north");   //标识上居中水印
        waterPositionMap.put("3", "ne");      //标识右上角水印
        waterPositionMap.put("4", "west");    //标识左居中水印
        waterPositionMap.put("5", "center");  //标识居中水印
        waterPositionMap.put("6", "east");    //标识右居中水印
        waterPositionMap.put("7", "sw");      //标识左下角水印
        waterPositionMap.put("8", "south");   //标识下居中水印
        waterPositionMap.put("9", "se");      //标识右下角水印
    }

    public static String getWaterPosition(String key){
        return waterPositionMap.get(key);
    }

    /**
     * 获取base64安全编码后的字符串
     * @param data 字符串的byte字节数组
     * @return
     */
    public static String safeUrlBase64Encode(byte[] data){
        String encodeBase64 = new Base64Encoder().encode(data);
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replaceAll("=", "");
        return safeBase64Str;
    }

    /**
     * 获取base64安全编码字符串的解码
     * @param safeBase64Str base64安全编码后的字符串
     * @return
     */
    public static byte[] safeUrlBase64Decode(final String safeBase64Str){
        String base64Str = safeBase64Str.replace('-', '+');
        base64Str = base64Str.replace('_', '/');
        int mod4 = base64Str.length()%4;
        if(mod4 > 0){
            base64Str = base64Str + "====".substring(mod4);
        }
        return new Base64Encoder().decode(base64Str);
    }

}
