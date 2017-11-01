package com.qslib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Dang on 10/20/2015.
 */
public class SharedUtils {
    public static final String TYPE_TEXT = "text/plain";
    public static final String TYPE_IMAGE = "image/*";

    /**
     * @param context
     * @param title
     * @param type:       SharedUtils.TYPE_TEXT || SharedUtils.TYPE_IMAGE
     * @param subject
     * @param content
     * @param file
     * @param packageName
     */
    public static void actionShare(Context context, String title, String type, String subject,
                                   String content, File file, String packageName) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType(type);

            if (!StringUtils.isEmpty(subject)) intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (!StringUtils.isEmpty(content)) intent.putExtra(Intent.EXTRA_TEXT, content);
            if (file != null && file.exists())
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

            if (!StringUtils.isEmpty(packageName)) {
                intent.setPackage(packageName);
                context.startActivity(intent);
            } else {
                context.startActivity(Intent.createChooser(intent, title));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * share content to sms
     *
     * @param context
     * @param content
     */
    public static void actionSendSms(Context context, String content) {
        try {
            if (StringUtils.isEmpty(content)) return;
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.putExtra("sms_body", content);
            sendIntent.setType("vnd.android-dir/mms-sms");
            context.startActivity(sendIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
