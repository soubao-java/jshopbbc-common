package org.soubao.common.baidu.ueditor.upload;

import org.soubao.common.baidu.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Uploader {
    private HttpServletRequest request = null;
    private Map<String, Object> conf = null;
    private String url;
    public Uploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public final State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;

        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName),
                    this.conf);
        } else {
            state = new BinaryUploader().save(this.request, this.conf);
        }

        return state;
    }
}