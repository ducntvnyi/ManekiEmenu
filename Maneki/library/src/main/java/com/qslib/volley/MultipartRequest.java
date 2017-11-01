package com.qslib.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.qslib.logger.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by dangpp on 20/04/2016.
 */
@SuppressWarnings("ALL")
public class MultipartRequest extends Request<String> {
    private final HttpEntity mHttpEntity;
    private final Response.Listener mListener;
    private final Map<String, String> params;
    private final Map<String, File> files;

    public MultipartRequest(int method, String url, Map<String, String> params, Map<String, File> files,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.params = params;
        this.files = files;
        this.mListener = listener;
        this.mHttpEntity = buildMultipartEntity();
    }

    private HttpEntity buildMultipartEntity() {
        try {
            AndroidMultiPartEntity androidMultiPartEntity = new AndroidMultiPartEntity(num -> {
            });

            // add params
            if (params != null && params.size() > 0) {
                Logger.i("params", "==> params::" + params);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    androidMultiPartEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName("UTF-8")));
                }
            }

            // add files
            if (files != null && files.size() > 0) {
                Logger.i("files", "==> files::" + files);
                for (Map.Entry<String, File> entry : files.entrySet()) {
                    androidMultiPartEntity.addPart(entry.getKey(), new FileBody(entry.getValue()));
                }
            }

            // get total size of body
            long totalSize = androidMultiPartEntity.getContentLength();

            return androidMultiPartEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getBodyContentType() {
        try {
            if (mHttpEntity != null) return mHttpEntity.getContentType().getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            if (mHttpEntity != null) mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new String(response.data, "UTF-8"),
                    getCacheEntry());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // it should never happen though
        return Response.success(new String(response.data), getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        try {
            mListener.onResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
