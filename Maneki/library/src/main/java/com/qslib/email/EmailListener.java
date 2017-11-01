package com.qslib.email;

public interface EmailListener {
    void onStarted();

    void onSuccess();

    void onFail();

    void onFinished();
}
