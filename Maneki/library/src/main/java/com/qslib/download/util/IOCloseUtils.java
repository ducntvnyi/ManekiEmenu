package com.qslib.download.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Dang
 */
public class IOCloseUtils {
    public static final void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            synchronized (IOCloseUtils.class) {
                closeable.close();
            }
        }
    }
}
