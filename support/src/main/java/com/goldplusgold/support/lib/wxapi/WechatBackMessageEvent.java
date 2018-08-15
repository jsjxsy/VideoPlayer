package com.goldplusgold.support.lib.wxapi;

/**
 * Created by Administrator on 2017/4/28.
 */

public class WechatBackMessageEvent {
    private String code;

    public WechatBackMessageEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
