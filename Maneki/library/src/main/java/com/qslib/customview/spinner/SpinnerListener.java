package com.qslib.customview.spinner;

import android.widget.TextView;

/**
 * Created by Dang on 6/8/2016.
 */
public interface SpinnerListener<T> {
    void onSelected(int position, T entity);

    void onSetData(TextView tvContent, T entity);
}
