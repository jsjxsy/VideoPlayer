package utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

import java.util.Random;

/**
 * Created by admin on 2017/8/24.
 */

public class ImageUtils {
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }


    public static Bitmap toHeibai(Bitmap mBitmap) {
        int mBitmapWidth = 0;
        int mBitmapHeight = 0;

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        Bitmap bmpReturn = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight,
                Bitmap.Config.ARGB_8888);
        int iPixel = 0;
        for (int i = 0; i < mBitmapWidth; i++) {
            for (int j = 0; j < mBitmapHeight; j++) {
                int curr_color = mBitmap.getPixel(i, j);

                int avg = (Color.red(curr_color) + Color.green(curr_color) + Color
                        .blue(curr_color)) / 3;
                if (avg >= 100) {
                    iPixel = 255;
                } else {
                    iPixel = 0;
                }
                int modif_color = Color.argb(255, iPixel, iPixel, iPixel);

                bmpReturn.setPixel(i, j, modif_color);
            }
        }
        return bmpReturn;
    }

    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    public static Bitmap toMohu(Bitmap bmpSource, int Blur) {
        int mode = 5;
        Bitmap bmpReturn = Bitmap.createBitmap(bmpSource.getWidth(),
                bmpSource.getHeight(), Bitmap.Config.ARGB_8888);
        int pixels[] = new int[bmpSource.getWidth() * bmpSource.getHeight()];
        int pixelsRawSource[] = new int[bmpSource.getWidth()
                * bmpSource.getHeight() * 3];
        int pixelsRawNew[] = new int[bmpSource.getWidth()
                * bmpSource.getHeight() * 3];

        bmpSource.getPixels(pixels, 0, bmpSource.getWidth(), 0, 0,
                bmpSource.getWidth(), bmpSource.getHeight());

        for (int k = 1; k <= Blur; k++) {

            for (int i = 0; i < pixels.length; i++) {
                pixelsRawSource[i * 3 + 0] = Color.red(pixels[i]);
                pixelsRawSource[i * 3 + 1] = Color.green(pixels[i]);
                pixelsRawSource[i * 3 + 2] = Color.blue(pixels[i]);
            }

            int CurrentPixel = bmpSource.getWidth() * 3 + 3;

            for (int i = 0; i < bmpSource.getHeight() - 3; i++) {
                for (int j = 0; j < bmpSource.getWidth() * 3; j++) {
                    CurrentPixel += 1;
                    int sumColor = 0;
                    sumColor = pixelsRawSource[CurrentPixel
                            - bmpSource.getWidth() * 3];
                    sumColor = sumColor + pixelsRawSource[CurrentPixel - 3];
                    sumColor = sumColor + pixelsRawSource[CurrentPixel + 3];
                    sumColor = sumColor
                            + pixelsRawSource[CurrentPixel
                            + bmpSource.getWidth() * 3];
                    pixelsRawNew[CurrentPixel] = Math.round(sumColor / 4);
                }
            }

            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = Color.rgb(pixelsRawNew[i * 3 + 0],
                        pixelsRawNew[i * 3 + 1], pixelsRawNew[i * 3 + 2]);
            }
        }

        bmpReturn.setPixels(pixels, 0, bmpSource.getWidth(), 0, 0,
                bmpSource.getWidth(), bmpSource.getHeight());
        return bmpReturn;
    }


    public static Bitmap toYouHua(Bitmap bmpSource) {
        Bitmap bmpReturn = Bitmap.createBitmap(bmpSource.getWidth(),
                bmpSource.getHeight(), Bitmap.Config.RGB_565);
        int color = 0;
        int Radio = 0;
        int width = bmpSource.getWidth();
        int height = bmpSource.getHeight();

        Random rnd = new Random();
        int iModel = 10;
        int i = width - iModel;
        while (i > 1) {
            int j = height - iModel;
            while (j > 1) {
                int iPos = rnd.nextInt(100000) % iModel;
                color = bmpSource.getPixel(i + iPos, j + iPos);
                bmpReturn.setPixel(i, j, color);
                j = j - 1;
            }
            i = i - 1;
        }
        return bmpReturn;
    }

    public static Bitmap toFuDiao(Bitmap mBitmap) {


        int mBitmapWidth = 0;
        int mBitmapHeight = 0;

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        Bitmap bmpReturn = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight,
                Bitmap.Config.RGB_565);
        int preColor = 0;
        int prepreColor = 0;
        preColor = mBitmap.getPixel(0, 0);

        for (int i = 0; i < mBitmapWidth; i++) {
            for (int j = 0; j < mBitmapHeight; j++) {
                int curr_color = mBitmap.getPixel(i, j);
                int r = Color.red(curr_color) - Color.red(prepreColor) + 127;
                int g = Color.green(curr_color) - Color.red(prepreColor) + 127;
                int b = Color.green(curr_color) - Color.blue(prepreColor) + 127;
                int a = Color.alpha(curr_color);
                int modif_color = Color.argb(a, r, g, b);
                bmpReturn.setPixel(i, j, modif_color);
                prepreColor = preColor;
                preColor = curr_color;
            }
        }

        Canvas c = new Canvas(bmpReturn);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpReturn, 0, 0, paint);

        return bmpReturn;
    }
}
