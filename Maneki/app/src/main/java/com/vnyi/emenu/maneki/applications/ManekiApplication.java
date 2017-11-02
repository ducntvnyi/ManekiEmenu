package com.vnyi.emenu.maneki.applications;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.qslib.logger.Logger;
import com.qslib.util.FileUtils;
import com.vnyi.emenu.maneki.services.ManekiApiServices;
import com.vnyi.emenu.maneki.utils.Device;
import com.vnyi.emenu.maneki.utils.ManekiUtils;

import java.io.File;

/**
 * Created by Hungnd on 11/1/17.
 */

public class ManekiApplication extends Application {

    private static final String TAG = ManekiApplication.class.getSimpleName();

    private static File FilePathRootProject;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            MultiDex.install(this);
            // setup path project
            ManekiApplication.FilePathRootProject = FileUtils.getRootFolderPath(this, getPackageName());

            // setup Logger
            Logger.setEnableLog(true);
            Logger.setPathSaveLog(ManekiApplication.FilePathRootProject.getAbsolutePath(), getPackageName(), "log");

            ManekiPreference.getInstance(getApplicationContext()).putString(ManekiApiServices.MACHINE_NAME, Device.getDeviceName());
            ManekiPreference.getInstance(getApplicationContext()).putString(ManekiApiServices.MACHINE_ID, Device.getMachineId(getApplicationContext()));
            ManekiPreference.getInstance(getApplicationContext()).putInt(ManekiApiServices.LANG_ID, 1); // language default


        } catch (Exception e) {
            ManekiUtils.LogException(TAG, e);
        }
    }
}
