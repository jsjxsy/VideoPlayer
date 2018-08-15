package com.goldplusgold.support.lib.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Administrator on 2017/7/27.
 */

public class BitmapUtil {

    public static Bitmap scale(Bitmap bitmap,int widht,int height) {
        if(widht <= 0 || height<= 0 || bitmap.getWidth() <= 0){
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(widht/bitmap.getWidth() , height/bitmap.getHeight() ); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }
}
