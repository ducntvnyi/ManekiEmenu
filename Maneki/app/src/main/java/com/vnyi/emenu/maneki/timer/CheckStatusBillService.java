package com.vnyi.emenu.maneki.timer;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.qslib.network.NetworkUtils;
import com.qslib.sharepreferences.AppPreferences;
import com.qslib.soap.SoapListenerVyni;
import com.qslib.soap.SoapResponse;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.models.ConfigValueModel;
import com.vnyi.emenu.maneki.models.response.TicketLoadInfo;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.services.VnyiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

/**
 * Created by hungnd on 04/07/2016.
 */
public class CheckStatusBillService extends IntentService {

    public static final String TAG = CheckStatusBillService.class.getSimpleName();
    public static final String ACTION_NOTIFY = TAG;

    public static final String RESPONSE_MESSAGE = "ResponseMessage";

    public CheckStatusBillService() {
        super(CheckStatusBillService.class.getSimpleName());
        Log.e(TAG, "==> start CheckStatusBillService none");
    }

    public CheckStatusBillService(String name) {
        super(name);
        Log.d(TAG, "==> start CheckStatusBillService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        VnyiUtils.LogException(TAG, "<<--------------Start CheckStatusBillService------------>>");
        try {
            ConfigValueModel configValueModel = VnyiPreference.getInstance(getApplicationContext()).getObject(Constant.KEY_CONFIG_VALUE, ConfigValueModel.class);

            int langId = VnyiPreference.getInstance(getApplicationContext()).getInt(VnyiApiServices.LANG_ID);
            int yourVersion = VnyiPreference.getInstance(getApplicationContext()).getInt(VnyiApiServices.YOUR_VERSION);

            TicketLoadInfo ticketInfo = VnyiPreference.getInstance(getApplicationContext()).getObject(Constant.KEY_TICKET, TicketLoadInfo.class);
            int ticketId = ticketInfo.getTicketId();

            if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;
            String url = configValueModel.getLinkServer();


            if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) return;

            VnyiServices.requestGetCheckStatusBill(url, ticketId, langId, yourVersion, new SoapListenerVyni() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onSuccess(SoapResponse soapResponse) {
                    try {
                        if (soapResponse == null) return;

                        if (soapResponse.getStatus() != null) {
                            AppPreferences.getInstance(getApplicationContext()).putInt(VnyiApiServices.YOUR_VERSION, Integer.parseInt(soapResponse.getId()));
                            Intent broadcastIntent = new Intent();
                            broadcastIntent.setAction(ACTION_NOTIFY);
                            broadcastIntent.putExtra(RESPONSE_MESSAGE, soapResponse.getStatus());

                        }
                    } catch (Exception e) {
                        VnyiUtils.LogException(getApplicationContext(), "onSuccess", TAG, "==> checkStatusBill " + e.getMessage());
                    }
                }

                @Override
                public void onFail(Exception ex) {
                    VnyiUtils.LogException(getApplicationContext(), "onFail", TAG, "==> checkStatusBill " + ex.getMessage());
                }

                @Override
                public void onFinished() {

                }
            });
        } catch (Exception e) {
            VnyiUtils.LogException(getApplicationContext(), "Exception", TAG, "==> checkStatusBill " + e.getMessage());
        }
        VnyiUtils.LogException(TAG, "<<--------------end CheckStatusBillService------------>>");
    }

    private void notifySound() {
        try {
            long[] vibrator = {500, 1000};
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Log.e(TAG, "==> notifySound " + uri.getPath());
            notificationBuilder.setSound(uri);
            notificationBuilder.setVibrate(vibrator);
            notificationManager.notify(1, notificationBuilder.build());
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
