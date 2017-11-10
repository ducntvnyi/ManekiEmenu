package com.vnyi.emenu.maneki.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.qslib.logger.Logger;
import com.qslib.network.NetworkUtils;
import com.qslib.permission.PermissionUtils;
import com.qslib.sharepreferences.AppPreferences;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.qslib.util.StringUtils;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.BranchModel;
import com.vnyi.emenu.maneki.models.UserModel;
import com.vnyi.emenu.maneki.models.response.Branch;
import com.vnyi.emenu.maneki.models.response.UserOrder;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;

import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import java8.util.stream.StreamSupport;

/**
 * Created by Hungnd on 11/1/17.
 */

public class VnyiUtils {

    private static final String TAG = "Maneki";

    /**
     * get language code from cache
     *
     * @param context
     * @return
     */
    public static String getLanguageApp(Context context) {
        int langId = AppPreferences.getInstance(context).getInt(VnyiApiServices.LANG_ID);
        String language = getLanguage(langId);
        Log.d(TAG, "==> VnyiUtils langId::" + langId);
        Log.d(TAG, "==> VnyiUtils language::" + language);
        if (StringUtils.isEmpty(language)) language = Constant.LOCALE_VI;
        return language;
    }

    /**
     * get language code from langId
     *
     * @param langId
     * @return
     */
    private static String getLanguage(int langId) {
        if (langId == 1) {
            return Constant.LOCALE_VI;
        } else if (langId == 2) {
            return Constant.LOCALE_EN;
        }
        return "";
    }

    public static Locale stringToLocale(String s) {
        if (s == null) return null;
        StringTokenizer tempStringTokenizer = new StringTokenizer(s, "_");
        String lang = null;
        String country = null;
        if (tempStringTokenizer.hasMoreTokens())
            lang = (String) tempStringTokenizer.nextElement();
        if (tempStringTokenizer.hasMoreTokens())
            country = (String) tempStringTokenizer.nextElement();

        if (country != null)
            return new Locale(lang, country);
        else
            return new Locale(lang);
    }

    /**
     * log error message to file, server
     *
     * @param tag
     * @param ex
     */
    public static void LogException(String tag, Exception ex) {
        try {
            // log to file
            Logger.e(tag, ex);
            // TODO: log to server
        } catch (Exception e) {
            LogException(TAG, e);
        }
    }

    /**
     * @param tag
     * @param ex
     */
    public static void LogException(String tag, String ex) {
        try {
            // log to file
            Logger.e(tag, ex);
            // TODO: log to server
        } catch (Exception e) {
            LogException(TAG, e);
        }
    }

    /**
     * request permission
     *
     * @param activity
     * @return
     */
    public static boolean requestPermission(Activity activity) {
        String[] perms = {android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.ACCESS_WIFI_STATE, android.Manifest.permission.CHANGE_NETWORK_STATE,
                android.Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        return PermissionUtils.requestPermission(activity, PermissionUtils.REQUEST_CODE_PERMISSION, perms);
    }

    /**
     * save the error to server
     *
     * @param context
     * @param apiError
     * @param exception
     */
    public static void saveErrorSys(Context context, String apiError, String exception) {
        try {
            Log.e(TAG, "----------- Start saveErrorSys ------------------");

            int objId = 5;
            String loginName = "Admin";
            int langId = 1;
            String machineName = Device.getDeviceName();
            String machineID = Device.getMachineId(context);

            if (!NetworkUtils.isNetworkAvailable(context)) return;

            VnyiServices.requestPostSysLogErrorClient(Constant.KEY_CONFIG_URL, machineID, machineName, objId, loginName, "", "", apiError, exception, "", "", "", langId, new SoapListenerVyni() {
                @Override
                public void onStarted() {
                    Log.e(TAG, "==> onStarted");
                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    Log.e(TAG, "==> onSuccess");
                }

                @Override
                public void onFail(Exception ex) {
                    Log.e(TAG, "==> saveErrorSys " + ex.getMessage());
                }

                @Override
                public void onFinished() {

                }
            });
            Log.e(TAG, "----------- end saveErrorSys ------------------");

        } catch (Exception e) {
            Log.e(TAG, "==> saveErrorSys " + e.getMessage());
        }
    }

    public static String getLinkServer(Context context) {
        try {
            return VnyiPreference.getInstance(context).getString(Constant.KEY_LINK_SERVER);
        } catch (Exception e) {
            return "";
        }
    }


    public static BranchModel getListBranch(Context context) {
        try {
            return VnyiPreference.getInstance(context).getObject(Constant.KEY_LIST_BRANCH, BranchModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static Branch getBranchName(List<Branch> branchList, int branchId) {
        try {
            return StreamSupport.stream(branchList).filter(branch1 -> branch1.getBranchId() == branchId).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }

    public static UserModel getListUser(Context context) {
        try {
            return VnyiPreference.getInstance(context).getObject(Constant.KEY_LIST_USER_ORDER, UserModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static UserOrder getUserName(List<UserOrder> userOrders, int userId) {
        try {
            return StreamSupport.stream(userOrders).filter(userOrder -> userOrder.getObjAutoId() == userId).findFirst().get();
        } catch (Exception e) {
            return null;
        }

    }
}
