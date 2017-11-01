package com.qslib.download.core;


import com.qslib.download.DownloadConfiguration;
import com.qslib.download.DownloadException;
import com.qslib.download.DownloadInfo;
import com.qslib.download.DownloadRequest;
import com.qslib.download.architecture.ConnectTask;
import com.qslib.download.architecture.DownloadResponse;
import com.qslib.download.architecture.DownloadStatus;
import com.qslib.download.architecture.DownloadTask;
import com.qslib.download.architecture.Downloader;
import com.qslib.download.db.DatabaseManager;
import com.qslib.download.db.ThreadInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Dang on 2015/10/28.
 */
public class DownloaderImpl implements Downloader, ConnectTask.OnConnectListener, DownloadTask.OnDownloadListener {
    private DownloadRequest mRequest;
    private DownloadResponse mDownloadResponse;
    private Executor mExecutor;
    private DatabaseManager mDBManager;
    private DownloadConfiguration mConfig;
    private OnDownloaderDestroyedListener mListener;
    private DownloadInfo mDownloadInfo;
    private ConnectTask mConnectTask;

    private String mTag;
    private int mStatus;

    private List<DownloadTask> mDownloadTasks;

    public DownloaderImpl(DownloadRequest request, DownloadResponse downloadResponse, Executor executor, DatabaseManager dbManager,
                          String key, DownloadConfiguration config, OnDownloaderDestroyedListener listener) {
        mRequest = request;
        mDownloadResponse = downloadResponse;
        mExecutor = executor;
        mDBManager = dbManager;
        mTag = key;
        mConfig = config;
        mListener = listener;
        init();
    }

    private void init() {
        mDownloadInfo = new DownloadInfo(mRequest.getTitle().toString(), mRequest.getUri(), mRequest.getFolder());
        mDownloadTasks = new LinkedList<>();
    }

    @Override
    public boolean isRunning() {
        return mStatus == DownloadStatus.STATUS_STARTED
                || mStatus == DownloadStatus.STATUS_CONNECTING
                || mStatus == DownloadStatus.STATUS_CONNECTED
                || mStatus == DownloadStatus.STATUS_PROGRESS;
    }

    @Override
    public void start() {
        try {
            mStatus = DownloadStatus.STATUS_STARTED;
            mDownloadResponse.onStarted();
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            if (mConnectTask != null) {
                mConnectTask.cancel();
            }
            for (DownloadTask task : mDownloadTasks) {
                task.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancel() {
        try {
            if (mConnectTask != null) {
                mConnectTask.cancel();
            }
            for (DownloadTask task : mDownloadTasks) {
                task.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        try {
            // trigger the onDestroy callback tell download manager
            mListener.onDestroyed(mTag, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnecting() {
        try {
            mStatus = DownloadStatus.STATUS_CONNECTING;
            mDownloadResponse.onConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnected(long time, long length, boolean isAcceptRanges) {
        try {
            mStatus = DownloadStatus.STATUS_CONNECTED;
            mDownloadResponse.onConnected(time, length, isAcceptRanges);

            mDownloadInfo.setAcceptRanges(isAcceptRanges);
            mDownloadInfo.setLength(length);
            download(length, isAcceptRanges);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectFailed(DownloadException de) {
        try {
            mStatus = DownloadStatus.STATUS_FAILED;
            mDownloadResponse.onConnectFailed(de);
            onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectCanceled() {
        try {
            mStatus = DownloadStatus.STATUS_CANCELED;
            mDownloadResponse.onConnectCanceled();
            onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadConnecting() {
    }

    @Override
    public void onDownloadProgress(long finished, long length) {
        try {
            mStatus = DownloadStatus.STATUS_PROGRESS;
            // calculate percent
            final int percent = (int) (finished * 100 / length);
            mDownloadResponse.onDownloadProgress(finished, length, percent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadCompleted() {
        try {
            if (isAllComplete()) {
                deleteFromDB();
                mStatus = DownloadStatus.STATUS_COMPLETED;
                mDownloadResponse.onDownloadCompleted();
                onDestroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadPaused() {
        try {
            if (isAllPaused()) {
                mStatus = DownloadStatus.STATUS_PAUSED;
                mDownloadResponse.onDownloadPaused();
                onDestroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadCanceled() {
        try {
            if (isAllCanceled()) {
                deleteFromDB();
                mStatus = DownloadStatus.STATUS_CANCELED;
                mDownloadResponse.onDownloadCanceled();
                onDestroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadFailed(DownloadException de) {
        try {
            if (isAllFailed()) {
                mStatus = DownloadStatus.STATUS_FAILED;
                mDownloadResponse.onDownloadFailed(de);
                onDestroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            mConnectTask = new ConnectTaskImpl(mRequest.getUri(), this);
            mExecutor.execute(mConnectTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void download(long length, boolean acceptRanges) {
        try {
            initDownloadTasks(length, acceptRanges);
            // start tasks
            for (DownloadTask downloadTask : mDownloadTasks) {
                mExecutor.execute(downloadTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDownloadTasks(long length, boolean acceptRanges) {
        try {
            mDownloadTasks.clear();
            if (acceptRanges) {
                List<ThreadInfo> threadInfos = getMultiThreadInfos(length);
                // init finished
                int finished = 0;
                for (ThreadInfo threadInfo : threadInfos) {
                    finished += threadInfo.getFinished();
                }
                mDownloadInfo.setFinished(finished);
                for (ThreadInfo info : threadInfos) {
                    mDownloadTasks.add(new MultiDownloadTask(mDownloadInfo, info, mDBManager, this));
                }
            } else {
                ThreadInfo info = getSingleThreadInfo();
                mDownloadTasks.add(new SingleDownloadTask(mDownloadInfo, info, this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ThreadInfo> getMultiThreadInfos(long length) {
        try {
            // init threadInfo from db
            final List<ThreadInfo> threadInfos = mDBManager.getThreadInfos(mTag);
            if (threadInfos.isEmpty()) {
                final int threadNum = mConfig.getThreadNum();
                for (int i = 0; i < threadNum; i++) {
                    // calculate average
                    final long average = length / threadNum;
                    final long start = average * i;
                    final long end;
                    if (i == threadNum - 1) {
                        end = length;
                    } else {
                        end = start + average - 1;
                    }
                    ThreadInfo threadInfo = new ThreadInfo(i, mTag, mRequest.getUri(), start, end, 0);
                    threadInfos.add(threadInfo);
                }
            }

            return threadInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private ThreadInfo getSingleThreadInfo() {
        ThreadInfo threadInfo = new ThreadInfo(0, mTag, mRequest.getUri(), 0);
        return threadInfo;
    }

    private boolean isAllComplete() {
        boolean allFinished = true;
        for (DownloadTask task : mDownloadTasks) {
            if (!task.isComplete()) {
                allFinished = false;
                break;
            }
        }
        return allFinished;
    }

    private boolean isAllFailed() {
        boolean allFailed = true;
        for (DownloadTask task : mDownloadTasks) {
            if (task.isDownloading()) {
                allFailed = false;
                break;
            }
        }
        return allFailed;
    }

    private boolean isAllPaused() {
        boolean allPaused = true;
        for (DownloadTask task : mDownloadTasks) {
            if (task.isDownloading()) {
                allPaused = false;
                break;
            }
        }
        return allPaused;
    }

    private boolean isAllCanceled() {
        boolean allCanceled = true;
        for (DownloadTask task : mDownloadTasks) {
            if (task.isDownloading()) {
                allCanceled = false;
                break;
            }
        }
        return allCanceled;
    }

    private void deleteFromDB() {
        try {
            mDBManager.delete(mTag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
