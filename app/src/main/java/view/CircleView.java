package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xsy.android.videoplayer.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/8/25.
 */

public class CircleView extends View {

    private int circleSum;//圆的数量
    private int circleRadio;//圆的半径
    private int circleColor;//圆的颜色
    private int backColor;//背景颜色
    private Paint backPaint;//背景画笔
    private Paint circlePaint;//圆的画笔

    private Circle[] circles;
    private int[] direction = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private Random random;

    private float width;
    private float height;
    private int[] location = new int[2];


//    private Timer timer;
//    TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            Log.e("xsy", "timerTask run");
//            for (int i = 0; i < circleSum; i++) {
//                Circle c = circles[i];
//                if (c == null) {
//                    continue;
//                }
//                changeDirection(c);
//                //超出边界后重置方向
//                if (c.getX() > width || c.getX() < 0 || c.getY() > height || c.getY() < 0) {
//                    int d;
//                    while (true) {
//                        d = random.nextInt(direction.length);
//                        int lastD = c.getDirection();
//                        if ((lastD == 0 || lastD == 4 || lastD == 6 || lastD == 8 || lastD == 10) && (d == 0 || d == 4 || d == 6 || d == 8 || d == 10)) {//上
//                            continue;
//                        } else if ((lastD == 1 || lastD == 5 || lastD == 7 || lastD == 9) && (d == 1 || d == 5 || d == 7 || d == 9)) {//下
//                            continue;
//                        } else if ((lastD == 2 || lastD == 4 || lastD == 5 || lastD == 8 || lastD == 9) && (d == 2 || d == 4 || d == 5 || d == 8 || d == 9)) {//左
//                            continue;
//                        } else if ((lastD == 3 || lastD == 6 || lastD == 7 || lastD == 10) && (d == 3 || d == 6 || d == 7 || d == 10)) {//右
//                            continue;
//                        }
//                        break;
//                    }
//                    circles[i].setDirection(d);
//                }
//            }
//            postInvalidate();
//        }
//    };

    private void changeDirection(Circle c) {
        if (c == null) {
            return;
        }
        float dx = 0.5f;
        switch (c.getDirection()) {
            case 0://上
                c.setY(c.getY() - dx);
                break;
            case 1://下
                c.setY(c.getY() + dx);
                break;
            case 2://左
                c.setX(c.getX() - dx);
                break;
            case 3://右
                c.setX(c.getX() + dx);
                break;
            case 4://左上
                c.setX(c.getX() - dx);
                c.setY(c.getY() - dx);
                break;
            case 5://左下
                c.setX(c.getX() - dx);
                c.setY(c.getY() + dx);
                break;
            case 6://右上
                c.setX(c.getX() + dx);
                c.setY(c.getY() - dx);
                break;
            case 7://右下
                c.setX(c.getX() + dx);
                c.setY(c.getY() + dx);
                break;
            case 8://左上
                c.setX(c.getX() - dx);
                c.setY(c.getY() - dx * 2);
                break;
            case 9://左下
                c.setX(c.getX() - dx * 2);
                c.setY(c.getY() + dx);
                break;
            case 10://右上
                c.setX(c.getX() + dx);
                c.setY(c.getY() - dx * 2);
                break;
        }
    }

    public CircleView(Context context) {
        super(context);
        backPaint = new Paint();
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        circleSum = ta.getInteger(R.styleable.CircleView_circleSum, 10);
        circleColor = ta.getColor(R.styleable.CircleView_circleColor, Color.parseColor("#7756ffff"));
        backColor = ta.getColor(R.styleable.CircleView_backColor, Color.parseColor("#21efef"));
        circleRadio = ta.getInteger(R.styleable.CircleView_circleRadio, 60);
        ta.recycle();
        init();
    }

    /**
     * 初始化画笔
     */
    private void init() {
        backPaint = new Paint();
        backPaint.setColor(backColor);

        circlePaint = new Paint();
        circlePaint.setColor(circleColor);

    }

    /**
     * 比onDraw先执行
     * <p/>
     * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
     * 一个MeasureSpec由大小和模式组成
     * 它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小;
     * EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
     * AT_MOST(至多)，子元素至多达到指定大小的值。
     * <p/>
     * 　　它常用的三个函数：
     * 1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
     * 2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
     * 3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("xsy", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件宽度
        width = getMeasuredWidth();
        //获取控件高度
        height = getMeasuredHeight();
        //获取控件相对于父控件的位置坐标
        this.getLocationInWindow(location);

        //初始化圆
        initCircle();

    }

    /**
     * 初始化圆
     */
    private void initCircle() {
        circles = new Circle[circleSum];
        random = new Random();
        for (int i = 0; i < circleSum; i++) {
            int d = random.nextInt(direction.length);
            int x = random.nextInt((int) width);
            int y = random.nextInt((int) height);
            circles[i] = new Circle(x, y, d);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        canvas.drawRect(0, 0, location[0] + width, location[1] + height, backPaint);
        //遍历绘制每一个圆
        for (Circle c : circles) {
            canvas.drawCircle(c.getX(), c.getY(), circleRadio, circlePaint);
        }

//        if (timer == null) {
//            timer = new Timer();
//            timer.schedule(timerTask, 0, 100);
//        }

        for (int i = 0; i < circleSum; i++) {
            Circle c = circles[i];
            if (c == null) {
                continue;
            }
            changeDirection(c);
            //超出边界后重置方向
            if (c.getX() > width || c.getX() < 0 || c.getY() > height || c.getY() < 0) {
                int d;
                while (true) {
                    d = random.nextInt(direction.length);
                    int lastD = c.getDirection();
                    if ((lastD == 0 || lastD == 4 || lastD == 6 || lastD == 8 || lastD == 10) && (d == 0 || d == 4 || d == 6 || d == 8 || d == 10)) {//上
                        continue;
                    } else if ((lastD == 1 || lastD == 5 || lastD == 7 || lastD == 9) && (d == 1 || d == 5 || d == 7 || d == 9)) {//下
                        continue;
                    } else if ((lastD == 2 || lastD == 4 || lastD == 5 || lastD == 8 || lastD == 9) && (d == 2 || d == 4 || d == 5 || d == 8 || d == 9)) {//左
                        continue;
                    } else if ((lastD == 3 || lastD == 6 || lastD == 7 || lastD == 10) && (d == 3 || d == 6 || d == 7 || d == 10)) {//右
                        continue;
                    }
                    break;
                }
                circles[i].setDirection(d);
            }
        }
        postInvalidate();
    }

    /**
     * 不在窗口显示的时候停止线程
     */
    @Override
    protected void onDetachedFromWindow() {
//        if (timer != null) {
//            timer.cancel();
//        }
        super.onDetachedFromWindow();
    }


    class Circle {
        private float x;
        private float y;
        private int direction;

        public Circle(float x, float y, int d) {
            this.x = x;
            this.y = y;
            this.direction = d;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getY() {
            return y;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public int getDirection() {
            return direction;
        }
    }
}