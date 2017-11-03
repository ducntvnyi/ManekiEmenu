package com.vnyi.emenu.maneki.applications;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.qslib.logger.Logger;
import com.qslib.util.FileUtils;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.utils.Device;
import com.vnyi.emenu.maneki.utils.ManekiUtils;

import java.io.File;

/**
 * Created by Hungnd on 11/1/17.
 */

public class VnyiApplication extends Application {

    private static final String TAG = VnyiApplication.class.getSimpleName();

    private static File FilePathRootProject;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            MultiDex.install(this);
            // setup path project
            VnyiApplication.FilePathRootProject = FileUtils.getRootFolderPath(this, getPackageName());

            // setup Logger
            Logger.setEnableLog(true);
            Logger.setPathSaveLog(VnyiApplication.FilePathRootProject.getAbsolutePath(), getPackageName(), "log");

            VnyiPreference.getInstance(getApplicationContext()).putString(VnyiApiServices.MACHINE_NAME, Device.getDeviceName());
            VnyiPreference.getInstance(getApplicationContext()).putString(VnyiApiServices.MACHINE_ID, Device.getMachineId(getApplicationContext()));
            VnyiPreference.getInstance(getApplicationContext()).putInt(VnyiApiServices.LANG_ID, 1); // language default


        } catch (Exception e) {
            ManekiUtils.LogException(TAG, e);
        }
    }
}
