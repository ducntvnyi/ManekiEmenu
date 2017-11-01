package com.qslib.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qslib.library.R;
import com.qslib.logger.Logger;

import java.util.List;

/**
 * Created by Dang on 9/16/2015.
 */
public class SpinnerAdapter<T> extends BaseAdapter {
    private static final String TAG = SpinnerAdapter.class.getSimpleName();

    private List<T> entities;
    private Context context;
    private SpinnerListener<T> spinnerListener = null;

    public SpinnerAdapter(Context context, List<T> entities, SpinnerListener<T> spinnerListener) {
        this.context = context;
        this.entities = entities;
        this.spinnerListener = spinnerListener;
    }

    @Override
    public int getCount() {
        if (entities == null || entities.size() <= 0) return 0;
        return entities.size();
    }

    @Override
    public T getItem(int position) {
        if (entities == null || entities.size() <= 0) return null;
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder<T> viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_spinner, null);
            viewHolder = new ViewHolder<T>(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder<T>) convertView.getTag();
        }

        T entity = getItem(position);
        if (entity != null && spinnerListener != null) {
            viewHolder.setData(entity, spinnerListener);
            convertView.setOnClickListener((v) -> {
                try {
                    spinnerListener.onSelected(position, entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        return convertView;
    }

    static class ViewHolder<T> {
        public TextView tvContent;

        /**
         * @param view
         */
        public ViewHolder(View view) {
            tvContent = (TextView) view.findViewById(R.id.tvContent);
        }

        /**
         * @param entity
         * @param dropdownListener
         */
        public void setData(T entity, SpinnerListener<T> dropdownListener) {
            try {
                dropdownListener.onSetData(tvContent, entity);
            } catch (Exception ex) {
                Logger.e(TAG, ex.getMessage());
            }
        }
    }
}
