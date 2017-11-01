package com.qslib.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.qslib.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Dang on 5/27/2016.
 */
public class DownloadUtils {
    private static final String TAG = DownloadUtils.class.getSimpleName();
    public static final int DEFAULT_HTTP_CONNECT_TIMEOUT = 5 * 1000;
    public static final int DEFAULT_HTTP_READ_TIMEOUT = 20 * 1000;

    private Context context;
    private String link;
    private String path;
    private DownloadListener downloadListener;

    /**
     * get instance
     *
     * @param context
     * @return
     */
    public static DownloadUtils getInstance(Context context) {
        return new DownloadUtils(context);
    }

    /**
     * @param context
     */
    public DownloadUtils(Context context) {
        this.context = context;
    }

    /**
     * @param link (link to download)
     * @return
     */
    public DownloadUtils setLink(String link) {
        this.link = link;
        return this;
    }

    /**
     * @param path (path to save file)
     * @return
     */
    public DownloadUtils setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * @param downloadListener
     * @return
     */
    public DownloadUtils setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        return this;
    }

    /**
     * execute download
     */
    public void execute() {
        if (NetworkUtils.isNetworkAvailable(context)) return;

        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            downloadAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            downloadAsyncTask.execute();
        }
    }

    /**
     * listener
     */
    public interface DownloadListener {
        void onStart();

        void onSuccess();

        void onFail(Exception ex);

        void onFinish();
    }

    /**
     * model response
     */
    private class Response {
        private boolean result;
        private Exception exception;

        public Response() {
        }

        public Response(boolean result, Exception exception) {
            this.result = result;
            this.exception = exception;
        }

        public boolean isResult() {
            return result;
        }

        public Response setResult(boolean result) {
            this.result = result;
            return this;
        }

        public Exception getException() {
            return exception;
        }

        public Response setException(Exception exception) {
            this.exception = exception;
            return this;
        }
    }

    /**
     * download
     */
    class DownloadAsyncTask extends AsyncTask<Void, Void, Response> {
        private Response response = new Response();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (downloadListener != null) downloadListener.onStart();
        }

        @Override
        protected Response doInBackground(Void... params) {
            // valid link download
            if (StringUtils.isEmpty(link)) {
                response.setResult(false).setException(new Exception("Link download is null"));
                return response;
            }

            // valid path to save file
            if (StringUtils.isEmpty(path)) {
                response.setResult(false).setException(new Exception("Path save file is null"));
                return response;
            }

            // valid network
            if (!UrlUtils.isNetworkUrl(link)) {
                response.setResult(false).setException(new Exception("Network is not available"));
                return response;
            }

            Log.i(TAG, TAG + ":: Path:: " + link);
            Log.e(TAG, TAG + ":: TempPath:: " + path);

            URL url;
            HttpURLConnection http;
            InputStream inputStream = null;

            try {
                url = new URL(link);
                if (ImageDownloader.Scheme.ofUri(link) == ImageDownloader.Scheme.HTTPS) {
                    http = (HttpsURLConnection) (url != null ? url.openConnection() : null);
                } else {
                    http = (HttpURLConnection) (url != null ? url.openConnection() : null);
                }

                http.connect();
                http.setConnectTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
                http.setReadTimeout(DEFAULT_HTTP_READ_TIMEOUT);

                File fileSave = new File(path);
                FileOutputStream fileOutputStream = new FileOutputStream(fileSave);
                byte buffers[] = new byte[128];

                inputStream = http.getInputStream();
                do {
                    int numRead = inputStream.read(buffers);
                    if (numRead <= 0) break;
                    fileOutputStream.write(buffers, 0, numRead);
                } while (true);

                response.setResult(true).setException(null);
            } catch (IOException e) {
                response.setResult(false).setException(e);
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            // print message error
            if (response.getException() != null) response.getException().printStackTrace();

            // callback when download finished
            if (downloadListener != null) {
                downloadListener.onFinish();// finish
                if (response.isResult()) downloadListener.onSuccess();//   success
                else downloadListener.onFail(response.getException()); // fail
            }
        }
    }
}
