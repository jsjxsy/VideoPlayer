package utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * 为安装更新添加的工具类
 *
 * @author chenxing.pancx Date: 13-3-27 Time: 下午9:56 To change this template use File | Settings |
 *         File Templates.
 */
public class UpdateUtil {

    // install APK
    public static void installApk(File file, Activity activity) {
        String apkFileSignature = SignatureUtil.getSignatureOfApk(activity, file.getAbsolutePath());
        String currentAppSign = SignatureUtil.getCurrentAppSign(activity);
        if (!TextUtils.equals(apkFileSignature, currentAppSign)) {
            return;
        }

        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(activity, "com.xsy.android.videoplayer.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            // 执行的数据类型
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }

}
