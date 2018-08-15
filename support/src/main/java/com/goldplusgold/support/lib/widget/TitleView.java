package com.goldplusgold.support.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldplusgold.support.lib.R;


public class TitleView extends RelativeLayout {
    protected Context mContext;
    private ViewGroup mLayout;

    public TitleView(Context context) {
        super(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        mLayout = (ViewGroup) inflater.inflate(R.layout.titleview, this, true);
    }

    public LinearLayout getTitleLayout() {
        return (LinearLayout) mLayout.findViewById(R.id.layout_title);
    }

    public View getTitle() {
        return mLayout.findViewById(R.id.id_title_layout);
    }

    public TextView getPriceView() {
        return (TextView) mLayout.findViewById(R.id.txt_gold_price);
    }

    public ImageView getSloganView() {
        return (ImageView) mLayout.findViewById(R.id.iv_slogan);
    }

    public ImageView getBackView() {

        return (ImageView) mLayout.findViewById(R.id.iv_back);
    }

    public ImageView getGuideView() {
        return (ImageView) mLayout.findViewById(R.id.iv_flip);
    }

    public TextView getRightText() {
        return (TextView) mLayout.findViewById(R.id.txt_right);
    }

    public ImageView getNotificationView() {
        return (ImageView) mLayout.findViewById(R.id.iv_notification);
    }

    public ImageView getGoldTrendView() {
        return (ImageView) mLayout.findViewById(R.id.iv_gold_trend);
    }

    public ImageView getTDMoreView() {
        return (ImageView) mLayout.findViewById(R.id.iv_td_more);
    }

    public TextView getTitletextView() {
        return (TextView) mLayout.findViewById(R.id.title_txt);
    }

}
