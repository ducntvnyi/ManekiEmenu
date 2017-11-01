package com.qslib.soap;

public interface SoapListener {
    void onStarted();

    void onSuccess(SoapResponse soapResponse);

    void onFail(Exception ex);

    void onFinished();
}
