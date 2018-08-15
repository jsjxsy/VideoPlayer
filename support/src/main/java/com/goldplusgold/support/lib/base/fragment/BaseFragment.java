package com.goldplusgold.support.lib.base.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goldplusgold.support.lib.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 */
public abstract class BaseFragment extends Fragment {


    /**
     * Fragment当前状态是否可见
     */
    protected Toast mToast;
    protected Dialog mDialog;
    private Unbinder unbinder;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        //绑定fragment
        unbinder = ButterKnife.bind(this, view);

        Log.e("xsy","onCreateView");
        initView();
        initView(view);
        return view;
    }

    protected void initView() {
    }

    protected void initView(View v) {

    }

    abstract public int getLayoutId();

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public boolean isAvailableActivity() {
        return !(getActivity() == null || getActivity().isFinishing());
    }

    protected void showLoadingControl() {
        showLoadingControl(null, true);
    }

    protected void showLoadingControl(String msg, boolean isCancelable) {
        if (!isAvailableActivity()) {
            return;
        }
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.LoadingCustomDialog);
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
        if (!isAvailableActivity()) {
            return;
        }
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

    protected void showToastMessage(int msgId, int duration) {
        if (getActivity() == null) {
            return;
        }
        showToastMessage(getString(msgId), duration);
    }

    protected void showToastMessage(String msg, int duration) {
        if (getActivity() == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), msg, duration);
        }
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }

    protected void finishActivity() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        getActivity().finish();
    }


}
