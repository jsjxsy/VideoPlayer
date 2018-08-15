package com.goldplusgold.support.lib.utils;

/**
 * Created by Administrator on 2017/6/4.
 */

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

/**
 * EditText 数字输入范围过滤 (0 <= ? < MAX_VALUE && 小数点后保留 POINTER_LENGTH 位)
 *
 * @author gavin.xiong 2016/12/21
 */
public class NumRangeInputFilter implements InputFilter {

    // 只允许输入数字和小数点
    private static final String REGEX = "([0-9]|\\.)*";
    // 输入的最大金额
    private static double max = 300.00;
    // 小数点后的位数
    private static final int POINTER_LENGTH = 2;

    private static final String POINTER = ".";

    private static final String ZERO_ZERO = "00";


    public NumRangeInputFilter(double max) {
        this.max = max;

    }

    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        // 新输入的字符串为空（删除剪切等）
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }

        // 拼成字符串
        String temp = destText.substring(0, dstart)
                + sourceText.substring(start, end)
                + destText.substring(dend, dest.length());
        Log.v("temp", "-" + temp);

        // 纯数字加小数点
        if (!temp.matches(REGEX)) {
            Log.d("TAG", "!纯数字加小数点");
            return dest.subSequence(dstart, dend);
        }

        // 小数点的情况
        if (temp.contains(POINTER)) {
            // 第一位就是小数点
            if (temp.startsWith(POINTER)) {
                Log.d("TAG", "第一位就是小数点");
                return dest.subSequence(dstart, dend);
            }
            // 不止一个小数点
            if(temp.indexOf(POINTER) != temp.lastIndexOf(POINTER)) {
                Log.d("TAG", "不止一个小数点");
                return dest.subSequence(dstart, dend);
            }
        }

        double sumText = Double.parseDouble(temp);
        if (sumText >= max) {
            // 超出最大值
            Log.d("TAG", "超出最大值");
            //弹出止盈止损价限定范围提醒

            return dest.subSequence(dstart, dend);
        }

        // 有小数点的情况下
        if (temp.contains(POINTER)) {
            //验证小数点精度，保证小数点后只能输入两位
            if (!temp.endsWith(POINTER) && temp.split("\\.")[1].length() > POINTER_LENGTH) {
                Log.d("TAG", "保证小数点后只能输入两位");
                return dest.subSequence(dstart, dend);
            }
        } else if (temp.startsWith(POINTER) || temp.startsWith(ZERO_ZERO)) {
            // 首位只能有一个0
            Log.d("TAG", "首位只能有一个0");
            return dest.subSequence(dstart, dend);
        }

        Log.d("TAG", "正常情况");
        return source;
    }
}
