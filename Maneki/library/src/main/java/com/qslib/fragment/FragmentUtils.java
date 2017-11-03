package com.qslib.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.List;

import java8.util.function.Consumer;

/**
 * Created by Dang on 9/14/2015.
 */
public class FragmentUtils {
    private static int containerViewId = 0;

    /**
     * set content id
     *
     * @param containerViewId
     */
    public static void setContainerViewId(int containerViewId) {
        FragmentUtils.containerViewId = containerViewId;
    }

    /**
     * @param fragmentActivity
     * @param fragment
     */
    public static void replaceFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        replaceFragment(fragmentActivity, fragment, null);
    }

    /**
     * @param fragmentActivity
     * @param fragment
     * @param fragmentConsumer
     */
    public static void replaceFragment(FragmentActivity fragmentActivity, Fragment fragment, Consumer<Fragment> fragmentConsumer) {
        replaceFragment(fragmentActivity, containerViewId, fragment, fragmentConsumer);
    }

    /**
     * @param fragmentActivity
     * @param containerViewId
     * @param fragment
     */
    public static void replaceFragment(FragmentActivity fragmentActivity, int containerViewId, Fragment fragment) {
        replaceFragment(fragmentActivity, containerViewId, fragment, null);
    }

    /**
     * @param fragmentActivity
     * @param containerViewId
     * @param fragment
     * @param fragmentConsumer
     */
    public static void replaceFragment(FragmentActivity fragmentActivity, int containerViewId, Fragment fragment, Consumer<Fragment> fragmentConsumer) {
        try {
            fragmentActivity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, fragment)
                    .addToBackStack(null)
                    .commit();
            if (fragmentConsumer != null) fragmentConsumer.accept(fragment);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * clear cache fragment in view pager
     *
     * @param activity
     * @param fragments
     */
    public static void clearCacheFragment(FragmentActivity activity, List<Fragment> fragments) {
        try {
            // remove all cache in viewpager
            if (fragments != null && fragments.size() > 0) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (Fragment fragment : fragments) {
                    if (fragment != null) {
                        fragmentTransaction.remove(fragment);
                    }
                }
                fragmentTransaction.commit();
                // clear data
                fragments.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * clear fragment
     *
     * @param activity
     * @param fragment
     */
    public static void clearCacheFragment(FragmentActivity activity, Fragment fragment) {
        try {
            // remove all cache in viewpager

            if (fragment != null) {
                Log.e("clearCacheFragment","==> clearCacheFragment");
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragment);
                fragmentTransaction.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
