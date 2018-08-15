package com.goldplusgold.support.lib.base.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;

import com.goldplusgold.support.lib.R;
import com.goldplusgold.support.lib.utils.ScreenSizeUtil;

import java.util.ArrayList;

public class FragmentDialogBottomMenu extends FragmentBasicDialog implements OnItemClickListener {

    private final static String PICKED_UNIT_KEY = "picked_unit_key";
    private View content;
    private ArrayList<String> unitsArray = new ArrayList<String>();
    private int deviceWidth;
    private OnItemCheckListener mOnItemCheckListener;
    private String mPickedUnit;

    public static FragmentDialogBottomMenu newInstance(String pickedUnit) {
        FragmentDialogBottomMenu f = new FragmentDialogBottomMenu();
        Bundle args = new Bundle();
        f.setArguments(args);
        f.setStyle(STYLE_NO_TITLE, R.style.BottomMenuDialog);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mPickedUnit = args.getString(PICKED_UNIT_KEY);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomMenuDialog);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(getContentLayout(), container, false);
        initBodyContent(v);
        deviceWidth = ScreenSizeUtil.getDeviceWidth(getActivity());
        return v;

    }

    protected int getContentLayout() {
        return -1;
    }

    protected void initBodyContent(View v) {
//        content = v.findViewById(R.id.id_cotent_frag_dialog);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        content.setLayoutParams(new FrameLayout.LayoutParams(deviceWidth, FrameLayout.LayoutParams.WRAP_CONTENT));
//        adapter.setPickedUnit(mPickedUnit);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        mOnItemCheckListener.OnItemCheck(unit);
        dismiss();
    }

    public void setOnItemCheckListener(OnItemCheckListener mOnItemCheckListener) {
        this.mOnItemCheckListener = mOnItemCheckListener;
    }

    public void setArrayList(ArrayList<String> data) {
        unitsArray.addAll(data);
    }

//    public void RefreshData() {
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//    }

    public  interface OnItemCheckListener {

        public void OnItemCheck(String pickedUnit);
    }
}
