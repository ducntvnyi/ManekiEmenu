package com.qslib.soap;

public interface SoapListenerVyni {

    void onStarted();

    void onSuccess(SoapResponse soapResponse);

    void onFail(Exception ex);

    void onFinished();
}
