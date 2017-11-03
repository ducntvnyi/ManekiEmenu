package com.vnyi.emenu.maneki.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.qslib.customview.textview.ExtTextView;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.customviews.TextViewFont;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private static String TAG = MainActivity.class.getSimpleName();

    // index tab
    private static final int INDEX_MENU = 100;
    private static final int INDEX_ORDER = INDEX_MENU >> 1;
    private static final int INDEX_SALE_OFF = INDEX_MENU >> 2;
    private static final int INDEX_CALL_WAITER = INDEX_MENU >> 3;
    private static final int INDEX_CALL_PAYMENT = INDEX_MENU >> 4;
    private static final int INDEX_USE_APP = INDEX_MENU >> 5;
    private static final int INDEX_LANGUAGE = INDEX_MENU >> 6;

    @BindView(R.id.tvMenu)
    ExtTextView tvMenu;
    @BindView(R.id.tvOrder)
    ExtTextView tvOrder;
    @BindView(R.id.tvSaleOff)
    ExtTextView tvSaleOff;
    @BindView(R.id.tvCallWaiter)
    TextViewFont tvCallWaiter;
    @BindView(R.id.tvPayment)
    TextViewFont tvPayment;
    @BindView(R.id.tvUseApp)
    TextViewFont tvUseApp;
    @BindView(R.id.tvLanguage)
    TextViewFont tvLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        intiViews();
        loadData();

        onClickTab(INDEX_MENU);
    }

    private void intiViews() {

    }

    private void loadData() {

    }

    protected int getColorResource(int color) {
        return ContextCompat.getColor(getApplicationContext(), color);
    }

    /**
     * change text color when changed tab
     *
     * @param index
     */
    private void onChangeTextColor(int index) {
        switch (index) {
            case INDEX_MENU:
                tvMenu.setTextColor(getColorResource(R.color.color_red));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                tvLanguage.setTextColor(getColorResource(R.color.white));
                break;
            case INDEX_ORDER:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.color_red));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                tvLanguage.setTextColor(getColorResource(R.color.white));
                break;
            case INDEX_SALE_OFF:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.color_red));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                tvLanguage.setTextColor(getColorResource(R.color.white));
                break;
            case INDEX_CALL_WAITER:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.color_red));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                tvLanguage.setTextColor(getColorResource(R.color.white));
                break;
            case INDEX_CALL_PAYMENT:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.color_red));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                tvLanguage.setTextColor(getColorResource(R.color.white));
                break;
            case INDEX_USE_APP:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.color_red));
                tvLanguage.setTextColor(getColorResource(R.color.white));
                break;
            case INDEX_LANGUAGE:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                tvLanguage.setTextColor(getColorResource(R.color.color_red));
                break;
            default:
                tvMenu.setTextColor(getColorResource(R.color.color_red));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                tvLanguage.setTextColor(getColorResource(R.color.white));
                break;
        }
    }

    /**
     * Change background image when changed tab
     *
     * @param index
     */
    private void onChangeBackgroundTab(int index) {
        switch (index) {
            case INDEX_MENU:
                tvMenu.setBackgroundResource(R.mipmap.bg_btn_white);
                tvOrder.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvSaleOff.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                tvLanguage.setBackground(null);
                break;
            case INDEX_ORDER:
                tvMenu.setBackground(null);
                tvOrder.setBackgroundResource(R.mipmap.bg_btn_white);
                tvCallWaiter.setBackground(null);
                tvSaleOff.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                tvLanguage.setBackground(null);
                break;
            case INDEX_SALE_OFF:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackgroundResource(R.mipmap.bg_btn_white);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                tvLanguage.setBackground(null);
                break;
            case INDEX_CALL_WAITER:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackgroundResource(R.mipmap.bg_btn_white);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                tvLanguage.setBackground(null);
                break;
            case INDEX_CALL_PAYMENT:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackgroundResource(R.mipmap.bg_btn_white);
                tvUseApp.setBackground(null);
                tvLanguage.setBackground(null);
                break;
            case INDEX_USE_APP:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackgroundResource(R.mipmap.bg_btn_white);
                tvLanguage.setBackground(null);
                break;
            case INDEX_LANGUAGE:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                tvLanguage.setBackgroundResource(R.mipmap.bg_btn_white);
                break;
            default:
                tvMenu.setBackgroundResource(R.mipmap.bg_btn_white);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                tvLanguage.setBackground(null);
                break;
        }
    }

    /**
     * change state when changed tab
     *
     * @param index
     */
    private void onClickTab(final int index) {
        onChangeTextColor(index);
        onChangeBackgroundTab(index);
    }

    //-------- Start Listener for Onclick Tab (Navigation app) ----------
    @OnClick(R.id.tvMenu)
    void onClickMenu() {
        onClickTab(INDEX_MENU);
    }

    @OnClick(R.id.tvOrder)
    void onClickOrder() {
        onClickTab(INDEX_ORDER);
    }

    @OnClick(R.id.tvSaleOff)
    void onClickSaleOff() {
        onClickTab(INDEX_SALE_OFF);
    }

    @OnClick(R.id.tvCallWaiter)
    void onClickCallWaiter() {
        onClickTab(INDEX_CALL_WAITER);
    }

    @OnClick(R.id.tvPayment)
    void onClickCallPayment() {
        onClickTab(INDEX_CALL_PAYMENT);
    }

    @OnClick(R.id.tvUseApp)
    void onClickUseApp() {
        onClickTab(INDEX_USE_APP);
    }

    @OnClick(R.id.tvLanguage)
    void onClickChangeLanguage() {
        onClickTab(INDEX_LANGUAGE);
    }
    //-------- End Listener for Onclick Tab (Navigation app) ----------

    @OnClick(R.id.ivConfigHome)
    void onClickConfigHome() {
        // check config: true => go to.. : false: go to configScreen
    }
}
