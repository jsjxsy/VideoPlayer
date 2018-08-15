package common;


import android.os.Process;
import android.util.Log;

import com.goldplusgold.support.lib.base.BaseApplication;
import com.goldplusgold.support.lib.utils.AppCacheSharedPreferences;
import com.oubowu.slideback.ActivityHelper;

import org.polaric.colorful.Colorful;

/**
 * Created by admin on 2017/8/31.
 */

public class AppApplication extends BaseApplication {

    public static AppApplication appApplication;

    private ActivityHelper mActivityHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        Log.e("xsy", "AppApplication onCreate start");
        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);
        AppCacheSharedPreferences.init(this);
//        CrashHandler.getInstance().init(this);
        Log.e("xsy", "AppApplication onCreate");
        /**
         * 三方初始化放入工作线程，加速App启动
         */
        new Thread() {
            @Override
            public void run() {
                super.run();
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                Colorful.defaults()
                        .primaryColor(Colorful.ThemeColor.BLUE)
                        .accentColor(Colorful.ThemeColor.LIGHT_GREEN)
                        .translucent(false)
                        .night(false);
                Colorful.init(getApplicationContext());

            }
        }.start();
    }

    public static ActivityHelper getActivityHelper() {
        return appApplication.mActivityHelper;
    }


}
