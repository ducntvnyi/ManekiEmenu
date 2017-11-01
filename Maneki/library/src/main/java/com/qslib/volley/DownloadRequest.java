package com.qslib.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

/**
 * Created by Dang on 4/25/2016.
 */
public class DownloadRequest extends Request<byte[]> {
    private static final String TAG = DownloadRequest.class.getSimpleName();

    private Response.Listener<byte[]> mListener = null;
    private Map<String, String> mParams = null;

    public DownloadRequest(int method, String mUrl, Response.Listener<byte[]> listener,
                           Response.ErrorListener errorListener, Map<String, String> params) {
        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        this.mListener = listener;
        this.mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        try {
            //Pass the response data here
            return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(byte[] response) {
        try {
            mListener.onResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
