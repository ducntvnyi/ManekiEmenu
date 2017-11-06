package com.vnyi.emenu.maneki.applications;

import android.app.Application;
import android.support.multidex.MultiDex;


import com.qslib.fragment.FragmentUtils;
import com.qslib.logger.Logger;
import com.qslib.util.FileUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.utils.Device;

import java.io.File;

//import com.qslib.logger.Logger;
//import com.qslib.util.FileUtils;

/**
 * Created by Hungnd on 11/1/17.
 */

public class VnyiApplication extends Application {

    private static final String TAG = VnyiApplication.class.getSimpleName();

    private static File FilePathRootProject;
    private static VnyiApplication vnyiApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            MultiDex.install(this);
            vnyiApplication = this;
            // setup path project
            VnyiApplication.FilePathRootProject = FileUtils.getRootFolderPath(this, getPackageName());
            // setup fragment
            FragmentUtils.setContainerViewId(R.id.flMainContent);
            // setup Logger
            Logger.setEnableLog(true);
            Logger.setPathSaveLog(VnyiApplication.FilePathRootProject.getAbsolutePath(), getPackageName(), "log");

            VnyiPreference.getInstance(getApplicationContext()).putString(VnyiApiServices.MACHINE_NAME, Device.getDeviceName());
            VnyiPreference.getInstance(getApplicationContext()).putString(VnyiApiServices.MACHINE_ID, Device.getMachineId(getApplicationContext()));
            VnyiPreference.getInstance(getApplicationContext()).putInt(VnyiApiServices.LANG_ID, 1); // language default


        } catch (Exception e) {

        }
    }
    public static VnyiApplication getInstance() {
        return vnyiApplication;
    }
}
