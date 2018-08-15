package utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.goldplusgold.networklib.NetworkListener;
import com.goldplusgold.support.lib.base.activity.BaseActivity;
import com.goldplusgold.support.lib.utils.DeviceNetworkUtil;
import com.xsy.android.videoplayer.R;
import com.xsy.android.videoplayer.apiservice.ApiService;
import com.xsy.android.videoplayer.model.UpdateInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import view.UpdateDialog;

@SuppressWarnings("deprecation")
public class ApkUpdateControl {
    private static ApkUpdateControl instance = null;
    private final String mAppNameStr = "app.apk";
    private ProgressDialog mProgressDlg;
    private UpdateInfo model;
    private UpdateDialog updateDialog;

    private ApkUpdateControl() {

    }

    public synchronized static ApkUpdateControl getInstance() {
        if (instance == null) {
            instance = new ApkUpdateControl();
        }
        return instance;
    }

    private void showUpdateDialog(String enforce, final Activity context) {
        switch (enforce) {
            case "0":
                updateDialog = new UpdateDialog(context, model.getVersionName(), new String[]{"更新", "暂不更新"},
                        R.style.FullDialog, 1);
                break;
            case "1":
                updateDialog = new UpdateDialog(context, model.getVersionName(), new String[]{"更新"},
                        R.style.FullDialog, 2);
                break;
        }

        updateDialog.setAlterListener(new UpdateDialog.OnAlterListener() {

            @Override
            public void positive() {
                mProgressDlg.setTitle("开始加载");
                mProgressDlg.setMessage("请稍候...");
                downFile(model.getDownload(), context);
            }

            @Override
            public void nagative() {

            }

        });
        updateDialog.show();
    }

    private void initVariable(Activity context) {
        mProgressDlg = new ProgressDialog(context);
        mProgressDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        mProgressDlg.setIndeterminate(false);
    }


    public void checkVersion(final Activity context) {
        removeDownloadFile();
        initVariable(context);
        final String versionCodeString = DeviceNetworkUtil.getVersionCode(context);

        ApiService.getInstance().checkVersion(new NetworkListener() {
            @Override
            public void onSuccess(String message) {
                UpdateInfo versionModel = JSON.parseObject(message, UpdateInfo.class);
                if (versionModel == null) {
                    Toast.makeText(context, "服务器错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                model = versionModel;
                int versionCode = Integer.parseInt(versionCodeString);
                int updateVersionCode = Integer.parseInt(model.getVersionCode());
                if (updateVersionCode > versionCode) {
                    showUpdateDialog("0", context);
                }

            }

            @Override
            public void onFail(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downFile(final String url, final Activity context) {
        mProgressDlg.show();
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        mAppNameStr) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("ApkUpdateControl", "onError :" + e.getMessage());
                        ((BaseActivity) context).showToastMessage("更新失败!", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mProgressDlg.setProgress((int) (100 * progress));
                        Log.e("ApkUpdateControl", "inProgress" + (int) (100 * progress));
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e("ApkUpdateControl", "onResponse :" + file.getAbsolutePath());
                        mProgressDlg.dismiss();
                        UpdateUtil.installApk(file, context);
                    }
                });
    }


    /**
     * 删除已经下载文件
     */
    private void removeDownloadFile() {
        File downloadFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                mAppNameStr);
        if (downloadFile != null && downloadFile.exists()) {
            downloadFile.delete();
        }
    }

}
