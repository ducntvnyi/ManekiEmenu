package com.qslib.volley;

import android.support.annotation.NonNull;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by Dang on 3/1/2016.
 */
@SuppressWarnings("ALL")
public class AndroidMultiPartEntity extends MultipartEntity {
    private final ProgressListener listener;

    /**
     * @param listener
     */
    public AndroidMultiPartEntity(final ProgressListener listener) {
        super(HttpMultipartMode.BROWSER_COMPATIBLE);
        this.listener = listener;
    }

    /**
     * @param mode
     * @param listener
     */
    public AndroidMultiPartEntity(final HttpMultipartMode mode,
                                  final ProgressListener listener) {
        super(mode);
        this.listener = listener;
    }

    /**
     * @param mode
     * @param boundary
     * @param charset
     * @param listener
     */
    public AndroidMultiPartEntity(HttpMultipartMode mode, final String boundary,
                                  final Charset charset, final ProgressListener listener) {
        super(mode, boundary, charset);
        this.listener = listener;
    }

    @Override
    public void writeTo(final OutputStream outStream) throws IOException {
        super.writeTo(new CountingOutputStream(outStream, this.listener));
    }

    public interface ProgressListener {
        void transferred(long num);
    }

    public static class CountingOutputStream extends FilterOutputStream {
        private final ProgressListener listener;
        private long transferred;

        public CountingOutputStream(final OutputStream out,
                                    final ProgressListener listener) {
            super(out);
            this.listener = listener;
            this.transferred = 0;
        }

        public void write(@NonNull byte[] b, int off, int len) throws IOException {
            try {
                out.write(b, off, len);
                this.transferred += len;
                this.listener.transferred(this.transferred);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write(int b) throws IOException {
            try {
                out.write(b);
                this.transferred++;
                this.listener.transferred(this.transferred);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}