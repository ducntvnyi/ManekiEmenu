package com.qslib.countrypicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qslib.library.R;

import java.util.List;

@SuppressWarnings("ALL")
public class CountryAdapter extends BaseAdapter {
    private List<CountryEntity> countryEntities = null;
    private LayoutInflater inflater = null;

    /**
     * Constructor
     *
     * @param context
     * @param countryEntities
     */
    @SuppressWarnings("JavaDoc")
    public CountryAdapter(Context context, List<CountryEntity> countryEntities) {
        super();
        try {
            this.countryEntities = countryEntities;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        try {
            if (countryEntities == null || countryEntities.size() <= 0) return 0;
            return countryEntities.size();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public CountryEntity getItem(int position) {
        try {
            if (countryEntities == null || countryEntities.size() <= 0) return null;
            return countryEntities.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Return row for each country
     */
    @SuppressLint({"InflateParams", "DefaultLocale"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        try {
            if (convertView == null) {
                convertView = inflater
                        .inflate(R.layout.dialog_fragment_country_row, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // get data
            CountryEntity countryEntity = getItem(position);
            viewHolder.setData(countryEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView tvCode = null;
        public TextView tvContent = null;

        public ViewHolder(View view) {
            tvCode = (TextView) view
                    .findViewById(R.id.tvCode);
            tvContent = (TextView) view
                    .findViewById(R.id.tvContent);
        }

        /**
         * @param countryEntity
         */
        public void setData(CountryEntity countryEntity) {
            try {
                if (countryEntity != null) {
                    tvCode.setText(countryEntity.getCode().toUpperCase());
                    tvContent.setText(countryEntity.getName());
                } else {
                    tvCode.setText("");
                    tvContent.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}