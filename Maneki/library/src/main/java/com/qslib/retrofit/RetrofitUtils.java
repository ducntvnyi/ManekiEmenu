package com.qslib.retrofit;

import java.io.IOException;

import okhttp3.Request;
import okio.Buffer;

/**
 * Created by Administrator on 19/01/2017.
 */

public class RetrofitUtils {
    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return e.getMessage();
        }
    }
}
