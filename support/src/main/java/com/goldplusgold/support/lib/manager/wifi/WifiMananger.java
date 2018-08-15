package com.goldplusgold.support.lib.manager.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2017/7/18.
 */

public class WifiMananger {
    public final static int NetType_Not  = 0 ; //没有网络
    public final static int NetType_Flow = 1 ; //流量
    public final static int NetType_Wifi = 2 ; //wifi

    public final static int NetType_NotChangeFlow = 5 ; //没有网络变为移动流量
    public final static int NetType_NotChangeWifi = 6 ; //没有网络变为wifi
    public final static int NetType_FlowChangeWifi = 7 ;//移动流量变为wifi
    public final static int NetType_WifiChangeFlow = 8 ;//wifi变为移动流量
    public final static int NetType_WifiChangeNot = 9 ;//移动流量变为没有网络
    public final static int NetType_FlowChangeNot = 10 ;//wifi变为没有网络


    public int currtentstate ; //当前状态

    private Context context ;

   /* private static WifiMananger wifiMananger ;*/
    private BroadcastReceiver networkBroadcast;

    /*public static WifiMananger getIntance(Context context){
        if(wifiMananger == null){
            wifiMananger = new WifiMananger(context);
        }
        return wifiMananger ;
    }*/
    private WifiInterface wifiInterface ;

    public WifiMananger(Context context , WifiInterface wifiInterface) {
        this.context = context ;
        this.wifiInterface = wifiInterface ;
        judgeNet();
        registerBroadcast();
    }

    //判断网络的状态
    public void judgeNet() {
        ConnectivityManager mConnectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        //检查网络连接
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();

        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
            //没有网络连接
            currtentstate = NetType_Not ;
           // wifiInterface.CurrentNetState(NetType_Not);
            wifiInterface.state_not();

        }else{
            //有网络连接
            int netType = info.getType();
            int netSubtype = info.getSubtype();
            if(netType == ConnectivityManager.TYPE_WIFI){
                //网络状态为wifi
                currtentstate = NetType_Wifi ;
                //wifiInterface.CurrentNetState(NetType_Wifi);
                wifiInterface.state_wifi();
            } if (netType == ConnectivityManager.TYPE_MOBILE
                    /*&& netSubtype == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()*/) {   //MOBILE
                //网络状态为移动数据
                currtentstate = NetType_Flow ;
                //wifiInterface.CurrentNetState(NetType_Flow);
                wifiInterface.state_3G();
            }

        }
    }

    //监听网络状态的改变
    public void registerBroadcast() {
        networkBroadcast = new NetReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkBroadcast, filter);
    }

    public class NetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager mConnectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
            //检查网络连接
            NetworkInfo info = mConnectivity.getActiveNetworkInfo();

            if (info == null || !mConnectivity.getBackgroundDataSetting()) {
                //没有网络连接
                if(currtentstate != NetType_Not) {
                    currtentstate = NetType_Not;
                    //wifiInterface.CurrentNetState(NetType_Not);
                    wifiInterface.state_not();
                }

            }else{
                //有网络连接
                int netType = info.getType();
                int netSubtype = info.getSubtype();
                if(netType == ConnectivityManager.TYPE_WIFI){
                    //网络状态为wifi
                    if(currtentstate != NetType_Wifi) {
                        currtentstate = NetType_Wifi ;
                        //wifiInterface.CurrentNetState(NetType_Wifi);
                        wifiInterface.state_wifi();
                    }


                } if (netType == ConnectivityManager.TYPE_MOBILE
                    /*&& netSubtype == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()*/) {   //MOBILE
                    //网络状态为移动数据
                    if(currtentstate != NetType_Flow) {
                        currtentstate = NetType_Flow;
                        //wifiInterface.CurrentNetState(NetType_Flow);
                        wifiInterface.state_3G();
                    }
                }

            }



            /* int newState = -1;

            ConnectivityManager mConnectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
            //检查网络连接
            NetworkInfo info = mConnectivity.getActiveNetworkInfo();

            if (info == null || !mConnectivity.getBackgroundDataSetting()) {
                //没有网络连接
                newState = NetType_Not ;

            }else{
                //有网络连接
                int netType = info.getType();
                int netSubtype = info.getSubtype();
                if(netType == ConnectivityManager.TYPE_WIFI){
                    //网络状态为wifi
                    newState = NetType_Wifi ;
                } if (netType == ConnectivityManager.TYPE_MOBILE
                    /*&& netSubtype == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()) {   //MOBILE
                    //网络状态为移动数据
                    newState = NetType_Flow ;
                }

            }

           switch (currtentstate){
                case NetType_Not:
                    currtentstate = newState ;
                    if(newState == NetType_Flow){
                        wifiInterface.changeState(NetType_NotChangeFlow);
                    }else if(newState == NetType_Wifi){
                        wifiInterface.changeState(NetType_NotChangeWifi);
                    }
                    break;
                case NetType_Flow:
                    currtentstate = newState ;
                    if(newState == NetType_Not){
                        wifiInterface.changeState(NetType_FlowChangeNot);
                    }else if(newState == NetType_Wifi){
                        wifiInterface.changeState(NetType_FlowChangeWifi);
                    }
                    break;
                case NetType_Wifi:
                    currtentstate = newState ;
                    if(newState == NetType_Not){
                        wifiInterface.changeState(NetType_WifiChangeNot);
                    }else if(newState == NetType_Flow){
                        wifiInterface.changeState(NetType_WifiChangeFlow);
                    }
                    break;
            }*/

        }
    }

    public void unRegisterNetworkReceiver() {
        context.unregisterReceiver(networkBroadcast);
    }

}
