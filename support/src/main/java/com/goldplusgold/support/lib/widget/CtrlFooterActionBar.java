//package com.goldplusgold.support.lib.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import com.goldplusgold.support.lib.R;
//
//
///**
// * @author alibaba-guangming
// */
//public class CtrlFooterActionBar extends LinearLayout implements View.OnClickListener {
//
//    public static final int ACTION_MODE_LEFT = 1;
//    public static final int ACTION_MODE_RIGHT = 2;
//
//    private Button mButtonLeft;
//    private Button mButtonRight;
//    private OnFooterBarClickListener mListenerClicked;
//    private boolean mNewStyle = false;
//
//    public CtrlFooterActionBar(Context context) {
//        super(context);
//        initControl(context);
//    }
//
//    public CtrlFooterActionBar(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        TypedArray a = context.getTheme().obtainStyledAttributes(
//                attrs,
//                R.styleable.CtrlFooterActionBar,
//                0, 0);
//
//        try {
//            mNewStyle = a.getBoolean(R.styleable.CtrlFooterActionBar_newStyle, false);
//        } finally {
//            a.recycle();
//        }
//        initControl(context);
//    }
//
//    protected boolean isSetNewStyle() {
//        //return true if should use new style
//        return true;
//    }
//
//    protected void initControl(Context ctx) {
//        if (mNewStyle && isSetNewStyle()) {
//            LayoutInflater.from(getContext()).inflate(R.layout.view_ctrl_footer_action_bar, this, true);
//        } else {
//            LayoutInflater.from(getContext()).inflate(R.layout.view_ctrl_footer_action_bar_old, this, true);
//        }
//
//        mButtonLeft = (Button) findViewById(R.id.id_left_btn_ctrl_footer_action_bar);
//        mButtonRight = (Button) findViewById(R.id.id_right_btn_ctrl_footer_action_bar);
//        mButtonLeft.setOnClickListener(this);
//        mButtonRight.setOnClickListener(this);
//    }
//
//    public Button getButtonLeft() {
//        return this.mButtonLeft;
//    }
//
//    public Button getButtonRight() {
//        return this.mButtonRight;
//    }
//
//    public void setRightTextAndBackgroud(int text, int drawable) {
//        mButtonRight.setText(text);
//        mButtonRight.setTag(getResources().getText(text));
//        if (drawable != -1) {
//            mButtonRight.setBackgroundResource(drawable);
//        }
//    }
//
//    public void setRightTextAndBackgroud(String text, int drawable) {
//        mButtonRight.setText(text);
//        mButtonRight.setTag(text);
//        if (drawable != -1) {
//            mButtonRight.setBackgroundResource(drawable);
//        }
//
//    }
//
//    public void setRightTextSize(float textSize) {
//        mButtonRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//    }
//
//    public void setLeftTextAndBackgroud(int text, int drawable) {
//        mButtonLeft.setText(text);
//        mButtonLeft.setTag(getResources().getText(text));
//        if (drawable != -1) {
//            mButtonLeft.setBackgroundResource(drawable);
//        }
//    }
//
//    public void setLeftTextSize(float textSize) {
//        mButtonLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//    }
//
//    public void setLeftTextAndBackgroud(String text, int drawable) {
//        mButtonLeft.setText(text);
//        mButtonLeft.setTag(text);
//        if (drawable != -1) {
//            mButtonLeft.setBackgroundResource(drawable);
//        }
//    }
//
//    public void setButtonLeftEnable(boolean enabled) {
//        mButtonLeft.setEnabled(enabled);
//    }
//
//    public void setButtonLeftVisibility(int visibility) {
//        mButtonLeft.setVisibility(visibility);
//    }
//
//    public void setButtonRightEnable(boolean enable) {
//        mButtonRight.setEnabled(enable);
//    }
//
//    public void setButtonRightVisibility(int visibility) {
//        mButtonRight.setVisibility(visibility);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (mListenerClicked == null) {
//            return;
//        }
//
//        String text = v.getTag().toString();
//        if (TextUtils.isEmpty(text)) {
//            return;
//        }
//        mListenerClicked.onFooterActionBarClicked(text);
//    }
//
//    public void setOnFooterBarClickedListener(OnFooterBarClickListener mListenerClicked) {
//        this.mListenerClicked = mListenerClicked;
//    }
//
//    public interface OnFooterBarClickListener {
//
//        void onFooterActionBarClicked(String text);
//    }
//}
