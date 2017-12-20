package com.vnyi.emenu.maneki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.services.VnyiApiServices;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.LanguageUtil;
import com.vnyi.emenu.maneki.utils.VnyiUtils;

import static android.media.MediaFormat.KEY_LANGUAGE;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                VnyiPreference.getInstance(SplashActivity.this).putString(KEY_LANGUAGE, Constant.LOCALE_VI);
                VnyiPreference.getInstance(SplashActivity.this).putInt(VnyiApiServices.LANG_ID, 1);
                LanguageUtil.changeLanguageType(SplashActivity.this, VnyiUtils.stringToLocale(Constant.LOCALE_VI));
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
