package org.soubao.shop.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeUtil {
    /**
     * 时间戳转时间字符串
     */
    public static String timestampToStr(Long timestamp) {
        if (StringUtils.isEmpty(timestamp)) {
            return "";
        }
        timestamp *= 1000;
        Date date = new Date(timestamp);
        Date dateToday = new Date();
        Date dateYesterday = new Date(new Date().getTime() - (1000 * 60 * 60 * 24));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

        String dateStr = sdf.format(date);
        String dateTodayStr = sdf.format(dateToday);
        String dateYesterdayStr = sdf.format(dateYesterday);

        if (dateStr.equals(dateTodayStr)) {
            return "今天 " + sdf2.format(date);
        } else if (dateStr.equals(dateYesterdayStr)) {
            return "昨天 " + sdf2.format(date);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
        }
    }

    public static Long getTimestampForHour() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");//设置日期格式
        String dateStr = df.format(new Date());
        Long timestampForHour = null;
        try {
            timestampForHour = df.parse(dateStr).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestampForHour;
    }

    /**
     * 时间字符串转时间戳
     */
    public static Long transForSecond(String dateStr, String formatStr) {
        if(StringUtils.isEmpty(dateStr)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date result = new Date();
        try {
            result = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result.getTime() / 1000;
    }

    /**
     * 时间戳转日期
     */
    public static String transForDateStr(Long timestemp, String formatStr) {
        String str = "";
        if (timestemp != null && timestemp > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            try {
                str = sdf.format(timestemp * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }


    /**
     * 时间戳转商品访问记录时间字符串
     */
    public static String timestampToGoodsVisitDateStr(Long timestamp) {
        if (StringUtils.isEmpty(timestamp)) {
            return "";
        }
        timestamp *= 1000;
        Date date = new Date(timestamp);
        Date dateToday = new Date();
        Date dateYesterday = new Date(new Date().getTime() - (1000 * 60 * 60 * 24));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

        String dateStr = sdf.format(date);
        String dateTodayStr = sdf.format(dateToday);
        String dateYesterdayStr = sdf.format(dateYesterday);

        if (dateStr.equals(dateTodayStr)) {
            return "今天 " + sdf2.format(date);
        } else if (dateStr.equals(dateYesterdayStr)) {
            return "昨天 " + sdf2.format(date);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
        }
    }

}
