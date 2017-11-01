package com.qslib.volley;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

/**
 * Created by Dang on 10/20/2015.
 */
public class VolleyUtils {
    /**
     * private variable
     */
    private static RequestQueue requestQueue = null;

    /**
     * get instance request queue
     *
     * @param context
     */
    public static RequestQueue getInstance(Context context) {
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    /**
     * set timeout, default timeout is 30 seconds
     *
     * @param request
     */
    public static void setTimeOutVolley(Request request) {
        try {
            int socketTimeout = 360 * 1000;//360 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * set timeout
     *
     * @param seconds change to what you want
     * @param request
     */
    public static void setTimeOutVolley(int seconds, Request request) {
        try {
            int socketTimeout = seconds * 1000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
