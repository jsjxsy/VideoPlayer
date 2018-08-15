package com.goldplusgold.support.lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.goldplusgold.support.lib.R;
import com.goldplusgold.support.lib.utils.EditTextUtils;

import java.util.List;


/**
 * Created by Administrator on 2017/7/13.
 */

public class VerticalTimeLine extends View {

    private String time;
    private static int time_color = R.color.live_color_time;
    private static int line_color = R.color.live_color_line;
    private static int time_size = R.dimen.live_size_time;
    private int position = -1;
    float radius = -1f;
    int height;
    private float paddinLeft = -1f;
    private float textPaddinLeft = -1f;
    private float paddinTop = -1f;
    private Paint timePain;
    private Paint linePain;
    private Path path = new Path();;
    private float lineWidth = -1f;
    private float textWidth = -1;
    private float triangleW = -1f;
    private float triangleH = -1f;
    private float timePaddinTop = -1f;
    public VerticalTimeLine(Context context) {
        super(context);
        init();
    }

    public VerticalTimeLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalTimeLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        timePain = new Paint();
        timePain.setAntiAlias(true);
        timePain.setColor(this.getResources().getColor(time_color));
        timePain.setTextSize(this.getResources().getDimensionPixelSize(time_size));

        linePain = new Paint();
        linePain.setAntiAlias(true);
        linePain.setStrokeWidth(1.5f);
        linePain.setColor(this.getResources().getColor(R.color.live_color_line));

        radius = this.getResources().getDimensionPixelSize(R.dimen.live_size_radius);
        lineWidth = this.getResources().getDimensionPixelSize(R.dimen.live_size_width);
        paddinLeft = this.getResources().getDimensionPixelSize(R.dimen.live_text_pleft);
        textPaddinLeft = this.getResources().getDimensionPixelSize(R.dimen.live_line_pleft);
        triangleW = this.getResources().getDimensionPixelSize(R.dimen.live_triangle_width);
        triangleH = this.getResources().getDimensionPixelSize(R.dimen.live_triangle_height);
        paddinTop = this.getResources().getDimensionPixelSize(R.dimen.live_line_ptop);
        timePaddinTop = this.getResources().getDimensionPixelSize(R.dimen.live_time_ptop);
    }

    public void setSize(int height){
        this.height = height;
        requestLayout();
        invalidate();
    }


    public void setTime(String time){
        synchronized (this){
            this.time = time;
            textWidth = EditTextUtils.getTextWidth(timePain,time);
        }

    }

    public void setPosition(int position){
        this.position = position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(position == 0){
            drawZero(canvas);
        } else{
            drawOther(canvas);
        }
        
    }

    private void drawOther(Canvas canvas) {
        canvas.drawLine(paddinLeft+textWidth+textPaddinLeft+triangleW/2,0,paddinLeft+textWidth+textPaddinLeft+triangleW/2,height,linePain);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(paddinLeft+textWidth+textPaddinLeft+triangleW/2, timePaddinTop+radius, radius, linePain);// 大圆
        synchronized (this){
            if(time != null){
                canvas.drawText(time,paddinLeft,timePaddinTop*1.4f,timePain);
            }
        }
    }

    private void drawZero(Canvas canvas) {
        path.moveTo(paddinLeft+textWidth+textPaddinLeft+triangleW/2, paddinTop);
        path.lineTo(paddinLeft+textWidth+textPaddinLeft, paddinTop+triangleH);
        path.lineTo(paddinLeft+textWidth+textPaddinLeft+triangleW, paddinTop+triangleH);
        path.close();
        canvas.drawPath(path,linePain);
        canvas.drawLine(paddinLeft+textWidth+textPaddinLeft+triangleW/2,paddinTop+triangleH,paddinLeft+textWidth+textPaddinLeft+triangleW/2,height,linePain);
        drawCircle(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = this.getHeight() + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.max(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        return height;
    }
}
