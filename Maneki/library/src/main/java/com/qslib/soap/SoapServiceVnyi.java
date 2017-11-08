package com.qslib.soap;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dang on 6/2/2016.
 */
public class SoapServiceVnyi {
    //    private static final int DEFAULT_TIMEOUT = 200 * 1000; // 200 seconds
    private static final int DEFAULT_TIMEOUT = 120 * 1000; // 200 seconds
    // SoapEnvelope version
    private static int soapEnvelopeVersion = SoapEnvelope.VER12;

    private String nameSpace = "";
    private String url = "";
    private String methodName = "";
    private int timeOut = 0;

    private SoapListenerVyni soapListener = null;
    private SoapSerializationEnvelope soapEnvelope = null;
    private SoapObject soapObject = null;
    private List<HeaderProperty> headers = null;

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     */
    public static SoapServiceVnyi getInstance(String nameSpace, String url, String methodName) {
        return new SoapServiceVnyi(nameSpace, url, methodName, DEFAULT_TIMEOUT);
    }

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     * @param timeOut    (seconds)
     */
    public static SoapServiceVnyi getInstance(String nameSpace, String url, String methodName, int timeOut) {
        return new SoapServiceVnyi(nameSpace, url, methodName, timeOut);
    }

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     */
    public SoapServiceVnyi(String nameSpace, String url, String methodName) {
        this(nameSpace, url, methodName, DEFAULT_TIMEOUT);
    }

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     * @param timeOut    (seconds)
     */
    public SoapServiceVnyi(String nameSpace, String url, String methodName,
                           int timeOut) {
        this.nameSpace = nameSpace;
        this.url = url;
        this.methodName = methodName;
        this.timeOut = timeOut;
        // init value SoapEnvelope, SoapObject
        initSoapEnvelope();
        initSoapObject();
    }

    /**
     * init soap envelope
     */
    private void initSoapEnvelope() {
        try {
            soapEnvelope = new SoapSerializationEnvelope(soapEnvelopeVersion);
            soapEnvelope.implicitTypes = true;
            soapEnvelope.dotNet = true;
            soapEnvelope.setAddAdornments(false);
            MarshalDouble marshalDouble = new MarshalDouble();
            marshalDouble.register(soapEnvelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * init soap object
     */
    private void initSoapObject() {
        try {
            soapObject = new SoapObject(nameSpace, methodName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * add property to soap object
     *
     * @param propertyName (BillingAddress)
     * @param value
     * @return
     */
    public SoapServiceVnyi addPropertySoapObject(String propertyName, Object value) {
        try {
            soapObject.addProperty(propertyName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * @param propertyName (BillingAddress)
     * @param clazz        Address.Class
     * @return
     */
    public SoapServiceVnyi addMappingSoapEnvelope(String propertyName, Class clazz) {
        try {
            soapEnvelope.addMapping(nameSpace, propertyName, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * add header
     *
     * @param headers
     */
    public SoapServiceVnyi addHeader(List<HeaderProperty> headers) {
        try {
            this.headers = headers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SoapServiceVnyi setSoapListener(SoapListenerVyni soapListener) {
        try {
            this.soapListener = soapListener;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * get soap action
     *
     * @return
     */
    private String getSoapAction() {
        try {
            return nameSpace.endsWith("/") ? nameSpace + methodName : nameSpace + "/" + methodName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * execute service
     */
    public void execute() {
        try {
            AsyncTask<Void, Void, Object> asyncTask = new AsyncTask<Void, Void, Object>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    if (soapListener != null) soapListener.onStarted();
                }

                @Override
                protected Object doInBackground(Void... params) {
                    try {
                        Log.i("nameSpace", "nameSpace:: " + nameSpace);
                        Log.i("url", "url:: " + url);
                        Log.i("methodName", "methodName:: " + methodName);
                        Log.i("soapObject", "soapObject:: " + soapObject);

                        soapEnvelope.setOutputSoapObject(soapObject);

                        HttpTransportSE httpTransport = new HttpTransportSE(url, timeOut);

                        if (headers != null) {
                            httpTransport.call(getSoapAction(), soapEnvelope, headers);
                        } else {
                            httpTransport.call(getSoapAction(), soapEnvelope);
                        }

                        return soapEnvelope.getResponse();
                    } catch (IOException | XmlPullParserException ex) {
                        ex.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Object resultObject) {
                    super.onPostExecute(resultObject);
                    try {
                        if (soapListener != null) soapListener.onFinished();

                        if (resultObject instanceof SoapFault) {
                            SoapFault soupFault = (SoapFault) resultObject;
                            Exception ex = new Exception(soupFault.faultstring);
                            if (soapListener != null) soapListener.onFail(ex);
                        } else if (resultObject instanceof SoapObject) {
                            if (soapListener != null)
                                soapListener.onSuccess(getResponse((SoapObject) resultObject));
                        } else {
                            if (soapListener != null) soapListener.onSuccess(null);
                        }
                    } catch (Exception ex) {
                        if (soapListener != null) soapListener.onFail(ex);
                        ex.printStackTrace();
                    }
                }
            };

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                asyncTask.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get soap response from soap object
     *
     * @param data
     * @return
     */
    private SoapResponse getResponse(SoapObject data) {
        SoapResponse soapResponse = null;

        try {
            if (data != null && data.getPropertyCount() > 0) {
                soapResponse = new SoapResponse();
                soapResponse.setResult(data.getPropertySafelyAsString(SoapResponse.RESULT));
                soapResponse.setIsError(data.getPropertySafelyAsString(SoapResponse.IS_ERROR));
                soapResponse.setErrorMessage(data.getPropertySafelyAsString(SoapResponse.ERROR_MESSAGE));
                soapResponse.setErrorStackTrace(data.getPropertySafelyAsString(SoapResponse.ERROR_STACK_TRACE));
                soapResponse.setId(data.getPropertySafelyAsString(SoapResponse.ID));
                soapResponse.setStatus(data.getPropertySafelyAsString(SoapResponse.STATUS));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return soapResponse;
    }

}
