package com.qslib.download.architecture;

/**
 * Created by Dang on 2015/10/29.
 */
public interface Downloader {
    interface OnDownloaderDestroyedListener {
        void onDestroyed(String key, Downloader downloader);
    }

    boolean isRunning();

    void start();

    void pause();

    void cancel();

    void onDestroy();
}
