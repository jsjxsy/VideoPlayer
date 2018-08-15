package com.goldplusgold.support.lib.wxapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by admin on 2017/5/11.
 */

public class WeChatUtil {
    public static final String APP_ID = "wx18727c67f6902d29";
    public IWXAPI api;


    private static class SingleTonHolder {
        private static final WeChatUtil INSTANCE = new WeChatUtil();
    }

    public static WeChatUtil getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private WeChatUtil() {

    }

    public void regToWX(Context context) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    /**
     * 登录微信
     */
    public void WXLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        api.sendReq(req);

    }

}
