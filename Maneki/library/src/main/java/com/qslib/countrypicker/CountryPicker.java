package com.qslib.countrypicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import com.qslib.library.R;
import com.qslib.util.KeyboardUtils;
import com.qslib.util.StringUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

@SuppressWarnings("ALL")
public class CountryPicker extends DialogFragment implements
        Comparator<CountryEntity> {
    private static final String KEY_TITLE = CountryPicker.class.getSimpleName();

    private EditText etSearch = null;
    private ListView lvListView = null;

    private CountryAdapter adapter = null;
    private List<CountryEntity> countryEntities = null;
    private List<CountryEntity> selectedCountryEntities = null;
    private CountryListener countryListener = null;

    /**
     * Convenient function to read from raw file
     *
     * @param context
     * @return
     */
    private static String readFileAsString(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(
                    R.raw.countries);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) result.append(line);
            reader.close();

            return result.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * To support show as dialog
     *
     * @param dialogTitle
     * @return
     */
    public static CountryPicker newInstance(String dialogTitle) {
        CountryPicker countryPicker = new CountryPicker();

        // set title
        if (!StringUtils.isEmpty(null)) {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TITLE, null);
            countryPicker.setArguments(bundle);
        }

        return countryPicker;
    }

    /**
     * show country picker
     *
     * @return
     */
    public static CountryPicker newInstance() {
        return new CountryPicker();
    }

    /**
     * Set listener
     *
     * @param countryListener
     */
    public CountryPicker setCountryListener(CountryListener countryListener) {
        this.countryListener = countryListener;
        return this;
    }

    /**
     * get data from raw
     */
    private void getAllCountries() {
        try {
            if (countryEntities == null) {
                countryEntities = new ArrayList<>();
                // read from local file
                String allCountriesString = readFileAsString(getActivity());
                // convert string to json object
                JSONObject jsonObject = new JSONObject(allCountriesString);

                Iterator<?> keys = jsonObject.keys();
                // Add the data to all countries list
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    CountryEntity country = new CountryEntity();
                    country.setCode(key);
                    country.setName(jsonObject.getString(key));
                    countryEntities.add(country);
                }

                // Sort the all countries list based on country name
                Collections.sort(countryEntities, this);

                // Initialize selected countries with all countries
                selectedCountryEntities = new ArrayList<>();
                selectedCountryEntities.addAll(countryEntities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get countries from the json
        getAllCountries();
    }


    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // Inflate view
        return inflater.inflate(R.layout.country_dialog_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            // Set dialog title if show as dialog
            Bundle args = getArguments();
            if (args != null && args.containsKey(KEY_TITLE)) {
                String dialogTitle = args.getString(KEY_TITLE);
                getDialog().setTitle(dialogTitle);
            } else {
                this.getDialog().requestWindowFeature(STYLE_NO_TITLE);
            }

            // Get view components
            etSearch = (EditText) view.findViewById(R.id.etSearch);
            lvListView = (ListView) view.findViewById(R.id.lvCountry);

            view.findViewById(R.id.tvCancel).setOnClickListener(v -> {
                try {
                    etSearch.setText("");
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Set adapter
            adapter = new CountryAdapter(getActivity(), selectedCountryEntities);
            lvListView.setAdapter(adapter);

            // Inform listener
            lvListView.setOnItemClickListener((parent, v, position, id) -> {
                try {
                    if (countryListener != null) {
                        // hide keyboard
                        KeyboardUtils.hideSoftKeyboard(getActivity());
                        // delegate
                        CountryEntity countryEntity = selectedCountryEntities
                                .get(position);
                        countryListener.onSelected(countryEntity);
                        dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Search for which countries matched user query
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    search(s.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
    }

    /**
     * Search allCountriesList contains text and put result into
     * selectedCountriesList
     *
     * @param text
     */
    @SuppressLint("DefaultLocale")
    private void search(String text) {
        try {
            selectedCountryEntities.clear();

            if (!StringUtils.isEmpty(text)) {
                selectedCountryEntities = StreamSupport.stream(countryEntities).filter(n -> n.getCode().contains(text) || n.getName().contains(text)).collect(Collectors.toList());
            } else {
                selectedCountryEntities.addAll(countryEntities);
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Support sorting the countries list
     */
    @Override
    public int compare(CountryEntity lhs, CountryEntity rhs) {
        return lhs.getName().compareTo(rhs.getName());
    }
}
