package com.qslib.download;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.qslib.download.architecture.DownloadResponse;
import com.qslib.download.architecture.DownloadStatusDelivery;
import com.qslib.download.architecture.Downloader;
import com.qslib.download.core.DownloadResponseImpl;
import com.qslib.download.core.DownloadStatusDeliveryImpl;
import com.qslib.download.core.DownloaderImpl;
import com.qslib.download.db.DatabaseManager;
import com.qslib.download.db.ThreadInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Dang
 */
public class DownloadManager implements Downloader.OnDownloaderDestroyedListener {
    public static final String TAG = DownloadManager.class.getSimpleName();

    /**
     * singleton of DownloadManager
     */
    private static DownloadManager downloadManager;

    private DatabaseManager mDBManager;
    private Map<String, Downloader> mDownloaderMap;
    private DownloadConfiguration mConfig;
    private ExecutorService mExecutorService;
    private DownloadStatusDelivery mDelivery;

    public static DownloadManager getInstance() {
        if (downloadManager == null) {
            synchronized (DownloadManager.class) {
                downloadManager = new DownloadManager();
            }
        }
        return downloadManager;
    }

    /**
     * private construction
     */
    private DownloadManager() {
        mDownloaderMap = new LinkedHashMap<String, Downloader>();
    }

    public void init(Context context) {
        init(context, new DownloadConfiguration());
    }

    public void init(Context context, @NonNull DownloadConfiguration config) {
        if (config.getThreadNum() > config.getMaxThreadNum()) {
            throw new IllegalArgumentException("thread num must < max thread num");
        }

        mConfig = config;
        mDBManager = DatabaseManager.getInstance(context);
        mExecutorService = Executors.newFixedThreadPool(mConfig.getMaxThreadNum());
        mDelivery = new DownloadStatusDeliveryImpl(new Handler(Looper.getMainLooper()));
    }

    @Override
    public void onDestroyed(String key, Downloader downloader) {
        if (mDownloaderMap.containsKey(key)) {
            mDownloaderMap.remove(key);
        }
    }

    public void download(DownloadRequest request, String tag, CallBack callBack) {
        final String key = createKey(tag);
        if (check(key)) {
            DownloadResponse downloadResponse = new DownloadResponseImpl(mDelivery, callBack);
            Downloader downloader = new DownloaderImpl(request, downloadResponse, mExecutorService, mDBManager, key, mConfig, this);
            mDownloaderMap.put(key, downloader);
            downloader.start();
        }
    }

    public void pause(String tag) {
        String key = createKey(tag);
        if (mDownloaderMap.containsKey(key)) {
            Downloader downloader = mDownloaderMap.get(key);
            if (downloader != null) {
                if (downloader.isRunning()) {
                    downloader.pause();
                }
            }
            mDownloaderMap.remove(key);
        }
    }

    public void cancel(String tag) {
        String key = createKey(tag);
        if (mDownloaderMap.containsKey(key)) {
            Downloader downloader = mDownloaderMap.get(key);
            if (downloader != null) {
                downloader.cancel();
            }
            mDownloaderMap.remove(key);
        }
    }

    public void pauseAll() {
        for (Downloader downloader : mDownloaderMap.values()) {
            if (downloader != null) {
                if (downloader.isRunning()) {
                    downloader.pause();
                }
            }
        }
    }

    public void cancelAll() {
        for (Downloader downloader : mDownloaderMap.values()) {
            if (downloader != null) {
                if (downloader.isRunning()) {
                    downloader.cancel();
                }
            }
        }
    }

    public DownloadInfo getDownloadProgress(String tag) {
        String key = createKey(tag);
        List<ThreadInfo> threadInfos = mDBManager.getThreadInfos(key);
        DownloadInfo downloadInfo = null;
        if (!threadInfos.isEmpty()) {
            int finished = 0;
            int progress = 0;
            int total = 0;
            for (ThreadInfo info : threadInfos) {
                finished += info.getFinished();
                total += (info.getEnd() - info.getStart());
            }
            progress = (int) ((long) finished * 100 / total);
            downloadInfo = new DownloadInfo();
            downloadInfo.setFinished(finished);
            downloadInfo.setLength(total);
            downloadInfo.setProgress(progress);
        }
        return downloadInfo;
    }

    private boolean check(String key) {
        if (mDownloaderMap.containsKey(key)) {
            Downloader downloader = mDownloaderMap.get(key);
            if (downloader != null) {
                if (downloader.isRunning()) {
                    Log.w(TAG, "Task has been started!");
                    return false;
                } else {
                    throw new IllegalStateException("Downloader instance with same tag has not been destroyed!");
                }
            }
        }
        return true;
    }

    private static String createKey(String tag) {
        if (tag == null) {
            throw new NullPointerException("Tag can't be null!");
        }

        return String.valueOf(tag.hashCode());
    }
}
