package com.qslib.download.core;

import android.os.Handler;

import com.qslib.download.CallBack;
import com.qslib.download.architecture.DownloadStatus;
import com.qslib.download.architecture.DownloadStatusDelivery;

import java.util.concurrent.Executor;

/**
 * Created by Dang on 2015/7/15.
 */
public class DownloadStatusDeliveryImpl implements DownloadStatusDelivery {
    private Executor mDownloadStatusPoster;

    public DownloadStatusDeliveryImpl(final Handler handler) {
        mDownloadStatusPoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    @Override
    public void post(DownloadStatus DownloadStatus) {
        mDownloadStatusPoster.execute(new DownloadStatusDeliveryRunnable(DownloadStatus));
    }

    private static class DownloadStatusDeliveryRunnable implements Runnable {
        private final DownloadStatus mDownloadStatus;
        private final CallBack mCallBack;

        public DownloadStatusDeliveryRunnable(DownloadStatus DownloadStatus) {
            this.mDownloadStatus = DownloadStatus;
            this.mCallBack = mDownloadStatus.getCallBack();
        }

        @Override
        public void run() {
            try {
                switch (mDownloadStatus.getStatus()) {
                    case DownloadStatus.STATUS_CONNECTING:
                        mCallBack.onConnecting();
                        break;
                    case DownloadStatus.STATUS_CONNECTED:
                        mCallBack.onConnected(mDownloadStatus.getLength(), mDownloadStatus.isAcceptRanges());
                        break;
                    case DownloadStatus.STATUS_PROGRESS:
                        mCallBack.onProgress(mDownloadStatus.getFinished(), mDownloadStatus.getLength(), mDownloadStatus.getPercent());
                        break;
                    case DownloadStatus.STATUS_COMPLETED:
                        mCallBack.onCompleted();
                        break;
                    case DownloadStatus.STATUS_PAUSED:
                        mCallBack.onDownloadPaused();
                        break;
                    case DownloadStatus.STATUS_CANCELED:
                        mCallBack.onDownloadCanceled();
                        break;
                    case DownloadStatus.STATUS_FAILED:
                        mCallBack.onFailed(mDownloadStatus.getException());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
