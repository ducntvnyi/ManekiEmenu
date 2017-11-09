package com.qslib.soap;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dang on 6/2/2016.
 */
public class SoapService {
    private static final int DEFAULT_TIMEOUT = 200 * 1000; // 200 seconds
    // SoapEnvelope version
    private static int soapEnvelopeVersion = SoapEnvelope.VER12;

    private String nameSpace = "";
    private String url = "";
    private String methodName = "";
    private int timeOut = 0;

    private SoapListener soapListener = null;
    private SoapSerializationEnvelope soapEnvelope = null;
    private SoapObject soapObject = null;
    private List<HeaderProperty> headers = null;

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     */
    public static SoapService getInstance(String nameSpace, String url, String methodName) {
        return new SoapService(nameSpace, url, methodName, DEFAULT_TIMEOUT);
    }

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     * @param timeOut    (seconds)
     */
    public static SoapService getInstance(String nameSpace, String url, String methodName, int timeOut) {
        return new SoapService(nameSpace, url, methodName, timeOut);
    }

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     */
    public SoapService(String nameSpace, String url, String methodName) {
        this(nameSpace, url, methodName, DEFAULT_TIMEOUT);
    }

    /**
     * @param nameSpace  (http://www.abc.com/API/PublicJSON)
     * @param url        (http://www.abc.com/API/PublicJSON.asmx)
     * @param methodName (InsertAddress)
     * @param timeOut    (seconds)
     */
    public SoapService(String nameSpace, String url, String methodName,
                       int timeOut) {
        this.nameSpace = nameSpace;
        this.url = url;
        this.methodName = methodName;
        this.timeOut = timeOut;
        // init value SoapEnvelope, SoapObjectEntity
        initSoapEnvelope();
        initSoapObject();
    }

    /**
     * init soap envelope
     */
    private void initSoapEnvelope() {
        try {
            this.soapEnvelope = new SoapSerializationEnvelope(soapEnvelopeVersion);
            new MarshalBase64().register(this.soapEnvelope);   //serialization
            this.soapEnvelope.encodingStyle = SoapEnvelope.ENC;
            this.soapEnvelope.implicitTypes = true;
            this.soapEnvelope.dotNet = true;
            this.soapEnvelope.setAddAdornments(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * init soap object
     */
    private void initSoapObject() {
        try {
            this.soapObject = new SoapObject(this.nameSpace, this.methodName);
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
    public SoapService addPropertySoapObject(String propertyName, Object value) {
        try {

            this.soapObject.addProperty(propertyName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SoapService addPropertySoapObject(List<SoapObjectEntity> soapObjectEntities) {
        try {
            if (soapObjectEntities != null && soapObjectEntities.size() > 0) {
                for (SoapObjectEntity soapObjectEntity : soapObjectEntities) {
                    PropertyInfo propertyInfo = new PropertyInfo();
                    propertyInfo.setName(soapObjectEntity.getKey());
                    propertyInfo.setValue(soapObjectEntity.getValue());
                    propertyInfo.setType(byte[].class);
                    this.soapObject.addProperty(propertyInfo);
                }
            }
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
    public SoapService addMappingSoapEnvelope(String propertyName, Class clazz) {
        try {
            this.soapEnvelope.addMapping(this.nameSpace, propertyName, clazz);
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
    public SoapService addHeader(List<HeaderProperty> headers) {
        try {
            this.headers = headers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public SoapService setSoapListener(SoapListener soapListener) {
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
            return this.nameSpace.endsWith("/") ? this.nameSpace + this.methodName : this.nameSpace + "/" + this.methodName;
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
                            System.out.println("Fault string:: " + soupFault.faultstring);
                            Exception ex = new Exception(soupFault.faultstring);
                            if (soapListener != null) soapListener.onFail(ex);
                        } else if (resultObject instanceof SoapObject) {
                            String result = (String) ((SoapObject) resultObject).getInnerText();
                            System.out.println("Result:: " + result);
                            if (soapListener != null) {
                                soapListener.onSuccess(result);
                            }
                        } else if (resultObject instanceof SoapPrimitive) {
                            String result = ((SoapPrimitive) resultObject).getValue().toString();
                            System.out.println("Result:: " + result);
                            if (soapListener != null) {
                                soapListener.onSuccess(result);
                            }
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
}
