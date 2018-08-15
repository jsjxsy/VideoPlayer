package com.goldplusgold.support.lib.base.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goldplusgold.support.lib.R;
import com.goldplusgold.support.lib.utils.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by admin on 2017/5/2.
 */

public class BaseActivity extends AppCompatActivity {
    protected Unbinder unbinder;
    protected Toast mToast;
    protected Dialog mDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setTitleTransparent();
        setStatusBar();
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initView();
    }

    protected void initView() {

    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 布局文件
     *
     * @return
     */
    public int getLayoutId() {
        return R.layout.activity_base_layout;
    }

    public void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected void showToastMessage(int msgId, int duration) {

        showToastMessage(getString(msgId), duration);
    }

    public void showToastMessage(String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, duration);
        }
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }

    protected void showLoadingControl() {
        showLoadingControl(null, true);
    }

    protected void showLoadingControl(String msg, boolean isCancelable) {

        if (mDialog == null) {
            mDialog = new Dialog(this, R.style.LoadingCustomDialog);
            mDialog.setContentView(R.layout.layout_dialog_loading);
            ((ProgressBar) mDialog.findViewById(R.id.id_progress_dialog_loading)).getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
            mDialog.setCanceledOnTouchOutside(isCancelable);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    onDialogLoadingCancel();
                }
            });
        }
        mDialog.show();
    }

    protected void dismisLoadingControl() {
        dismissDialog();
    }

    private void dismissDialog() {

        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception ignored) {
        } finally {
            mDialog = null;
        }
    }

    public void onDialogLoadingCancel() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackCount <= 0) {
            finish();
            return;
        }
    }
}
