package com.vnyi.emenu.maneki.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.qslib.customview.textview.ExtTextView;
import com.qslib.fragment.FragmentUtils;
import com.qslib.util.BackUtils;
import com.qslib.util.KeyboardUtils;
import com.vnyi.emenu.maneki.R;
import com.vnyi.emenu.maneki.applications.VnyiPreference;
import com.vnyi.emenu.maneki.customviews.TextViewFont;
import com.vnyi.emenu.maneki.fragments.CallWaiterFragment;
import com.vnyi.emenu.maneki.fragments.ConfigFragment;
import com.vnyi.emenu.maneki.fragments.MenuFragment;
import com.vnyi.emenu.maneki.fragments.OrderFragment;
import com.vnyi.emenu.maneki.fragments.PaymentFragment;
import com.vnyi.emenu.maneki.fragments.SaleOffFragment;
import com.vnyi.emenu.maneki.fragments.UseAppFragment;
import com.vnyi.emenu.maneki.utils.Constant;
import com.vnyi.emenu.maneki.utils.LanguageUtil;
import com.vnyi.emenu.maneki.utils.VnyiContextWrapper;
import com.vnyi.emenu.maneki.utils.VyniUtils;

import java.util.Locale;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private static final String ENGLISH = "en";
    private static final String VIETNAMESE = "vi";
    private static final String KEY_LANGUAGE = "language";

    public int currentFragmentIndex = Constant.INDEX_CONFIG;
    public Stack<Fragment> fragments = new Stack<>();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        intiViews();
        loadData();
//        currentFragmentConstant.INDEX = VnyiPreference.getInstance(this).getInt(KEY_Constant.INDEX);
        changeTab(currentFragmentIndex);
    }
    @Override
    public void onBackPressed() {
        if (fragments != null && fragments.size() > 0) {
            try {
                KeyboardUtils.dontShowKeyboardActivity(this);
                if (fragments.size() > 1) {
                    // remove current fragment
                    fragments.pop();

                    // get previous fragment
                    Fragment fragment = fragments.peek();
                    if (fragment == null) return;

                    // back to previous fragment
                    FragmentUtils.replaceFragment(this, fragment);

                } else {
                    // init default fragment
//                    this.changeTabBottom(currentFragmentIndex, true);
                }
            } catch (Exception e) {
                VyniUtils.LogException(TAG, e);
            }
        } else {
            BackUtils.onClickExit(this, getString(R.string.double_tap_to_exit));
        }

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
            case Constant.INDEX_MENU:
                tvMenu.setTextColor(getColorResource(R.color.color_red));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                break;
            case Constant.INDEX_ORDER:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.color_red));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));

                break;
            case Constant.INDEX_SALE_OFF:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.color_red));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                break;
            case Constant.INDEX_CALL_WAITER:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.color_red));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                break;
            case Constant.INDEX_CALL_PAYMENT:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.color_red));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                break;
            case Constant.INDEX_USE_APP:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.color_red));
                break;
            case Constant.INDEX_LANGUAGE:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                break;
            case Constant.INDEX_CONFIG:
                tvMenu.setTextColor(getColorResource(R.color.white));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
                break;
            default:
                tvMenu.setTextColor(getColorResource(R.color.color_red));
                tvOrder.setTextColor(getColorResource(R.color.white));
                tvSaleOff.setTextColor(getColorResource(R.color.white));
                tvCallWaiter.setTextColor(getColorResource(R.color.white));
                tvPayment.setTextColor(getColorResource(R.color.white));
                tvUseApp.setTextColor(getColorResource(R.color.white));
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
            case Constant.INDEX_MENU:
                tvMenu.setBackgroundResource(R.mipmap.bg_btn_white);
                tvOrder.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvSaleOff.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                break;
            case Constant.INDEX_ORDER:
                tvMenu.setBackground(null);
                tvOrder.setBackgroundResource(R.mipmap.bg_btn_white);
                tvCallWaiter.setBackground(null);
                tvSaleOff.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                break;
            case Constant.INDEX_SALE_OFF:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackgroundResource(R.mipmap.bg_btn_white);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                break;
            case Constant.INDEX_CALL_WAITER:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackgroundResource(R.mipmap.bg_btn_white);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                break;
            case Constant.INDEX_CALL_PAYMENT:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackgroundResource(R.mipmap.bg_btn_white);
                tvUseApp.setBackground(null);
                break;
            case Constant.INDEX_USE_APP:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackgroundResource(R.mipmap.bg_btn_white);
                break;
            case Constant.INDEX_LANGUAGE:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                break;
            case Constant.INDEX_CONFIG:
                tvMenu.setBackground(null);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);
                break;
            default:
                tvMenu.setBackgroundResource(R.mipmap.bg_btn_white);
                tvOrder.setBackground(null);
                tvSaleOff.setBackground(null);
                tvCallWaiter.setBackground(null);
                tvPayment.setBackground(null);
                tvUseApp.setBackground(null);

                break;
        }
    }

    /**
     * onClick tab
     *
     * @param index
     */
    private void onClickTab(final int index) {
        onChangeTextColor(index);
        onChangeBackgroundTab(index);
    }

    /**
     * change state when changed tab
     *
     * @param index
     */
    public void changeTab(int index) {
        VnyiPreference.getInstance(this).putInt(Constant.KEY_INDEX, index);
        try {
            Fragment fragment;
            switch (index) {
                case Constant.INDEX_MENU:
                    fragment = MenuFragment.newInstance();
                    break;
                case Constant.INDEX_ORDER:
                    fragment = OrderFragment.newInstance();
                    break;
                case Constant.INDEX_SALE_OFF:
                    fragment = SaleOffFragment.newInstance();
                    break;
                case Constant.INDEX_CALL_WAITER:
                    fragment = CallWaiterFragment.newInstance();
                    break;
                case Constant.INDEX_CALL_PAYMENT:
                    fragment = PaymentFragment.newInstance();
                    break;
                case Constant.INDEX_USE_APP:
                    fragment = UseAppFragment.newInstance();
                    break;
                case Constant.INDEX_CONFIG:
                    fragment = ConfigFragment.newInstance();
                    break;
                default:
                    fragment = ConfigFragment.newInstance();

            }

            // replace fragment
            FragmentUtils.replaceFragment(this, fragment);
            Log.e(TAG, "==> change Tab: " + index);
            onClickTab(index);

            // set current index
            this.currentFragmentIndex = index;
            this.fragments.clear();
        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }
    }

    //-------- Start Listener for Onclick Tab (Navigation app) ----------
    @OnClick(R.id.tvMenu)
    void onClickMenu() {
        changeTab(Constant.INDEX_MENU);
    }

    @OnClick(R.id.tvOrder)
    void onClickOrder() {
        changeTab(Constant.INDEX_ORDER);
    }

    @OnClick(R.id.tvSaleOff)
    void onClickSaleOff() {
        changeTab(Constant.INDEX_SALE_OFF);
    }

    @OnClick(R.id.tvCallWaiter)
    void onClickCallWaiter() {
        changeTab(Constant.INDEX_CALL_WAITER);
    }

    @OnClick(R.id.tvPayment)
    void onClickCallPayment() {
        changeTab(Constant.INDEX_CALL_PAYMENT);
    }

    @OnClick(R.id.tvUseApp)
    void onClickUseApp() {
        changeTab(Constant.INDEX_USE_APP);
    }

    //-------- End Listener for Onclick Tab (Navigation app) ----------
    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = LanguageUtil.getLanguageType(this);
        super.attachBaseContext(VnyiContextWrapper.wrap(newBase, languageType));
    }

    @OnClick(R.id.llChangeLanguage)
    void onClickChangeLanguage() {
        try {
            if (VnyiPreference.getInstance(this).getString(KEY_LANGUAGE).equals(VIETNAMESE)) {
                VnyiPreference.getInstance(this).putString(KEY_LANGUAGE, ENGLISH);
                LanguageUtil.changeLanguageType(this, VyniUtils.stringToLocale(ENGLISH));

            } else {
                VnyiPreference.getInstance(getApplicationContext()).putString(KEY_LANGUAGE, VIETNAMESE);
                LanguageUtil.changeLanguageType(this, VyniUtils.stringToLocale(VIETNAMESE));
            }

        } catch (Exception e) {
            VyniUtils.LogException(TAG, e);
        }

    }

}
