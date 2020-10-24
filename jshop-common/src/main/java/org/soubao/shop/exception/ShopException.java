package org.soubao.shop.exception;

import org.soubao.starter.common.ResultEnum;

public class ShopException extends RuntimeException {
    public Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    private Object result;


    public ShopException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public ShopException(ResultEnum resultEnum, Object result) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.result = result;
    }

    public ShopException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    public Object getResult() {
        return result;
    }
}
