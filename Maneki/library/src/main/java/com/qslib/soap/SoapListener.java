package com.qslib.soap;

public interface SoapListener {
    void onStarted();

    void onSuccess(String result);

    void onFail(Exception ex);

    void onFinished();
}
