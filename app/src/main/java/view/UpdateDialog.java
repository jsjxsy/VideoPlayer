package view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsy.android.videoplayer.R;


public class UpdateDialog extends Dialog {
    private String titlecontent;
    private String[] button;
    private String title;
    private int mType;
    private TextView btn_nagative;
    private ImageView divideLine;
    private TextView btn_positive;
    private OnAlterListener AlterListener;
    private OnAfterDismissListener mDismissListener;

    public UpdateDialog(Context context, String titlecontent, String[] button, int theme, int type) {
        super(context, theme);
        this.titlecontent = titlecontent;
        this.button = button;
        this.mType = type;
    }

    public UpdateDialog(Context context, String titlecontent, String title, String[] button, int theme) {
        super(context, theme);
        this.titlecontent = titlecontent;
        this.button = button;
        this.title = title;
    }

    public UpdateDialog(Context context) {
        super(context);
    }

    public OnAfterDismissListener getDismissListener() {
        return mDismissListener;
    }

    public void setDismissListener(OnAfterDismissListener mDismissListener) {
        this.mDismissListener = mDismissListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    @Override
    public void onBackPressed() {
        if (mDismissListener != null) {
            mDismissListener.afterDismiss();
        } else {
            super.onBackPressed();
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_update_dialog);

        if (titlecontent == null) {
            return;
        }
        TextView tv_title = (TextView) this.findViewById(R.id.title);
        if (title != null && !title.equals("")) {
            tv_title.setText(title);
        }
        TextView tv_content = (TextView) this.findViewById(R.id.dialog_content);
        tv_content.setText(titlecontent);

        btn_positive = (TextView) this.findViewById(R.id.btn_positive);

        if (button == null || button.length != 2) {
            btn_positive.setText(getContext().getResources().getString(R.string.action_positive));
        } else {
            btn_positive.setText(button[0]);
        }

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (AlterListener == null)
                    return;
                AlterListener.positive();
            }
        });
        divideLine = (ImageView) findViewById(R.id.img_line);
        if (mType == 2) {
            divideLine.setVisibility(View.GONE);
        }
        btn_nagative = (TextView) this.findViewById(R.id.btn_nagative);

        if (button == null || button.length != 2) {
            btn_nagative.setVisibility(View.GONE);
        } else {
            btn_nagative.setText(button[1]);
        }

        btn_nagative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (AlterListener == null)
                    return;
                AlterListener.nagative();
            }
        });
        if (mType == 1) {
            setCanceledOnTouchOutside(true);
        } else {
            setCancelable(false);
        }
    }

    public OnAlterListener getAlterListener() {
        return AlterListener;
    }

    public void setAlterListener(OnAlterListener alterListener) {
        AlterListener = alterListener;
    }

    public interface OnAlterListener {
        public void positive();

        public void nagative();

    }

    public interface OnAfterDismissListener {
        public void afterDismiss();

    }
}
