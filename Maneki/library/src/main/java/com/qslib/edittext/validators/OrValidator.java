package com.qslib.edittext.validators;

/**
 * OR validator extend;
 *
 * @author Dang
 * @year 2016
 */

import android.widget.EditText;

import java.util.ArrayList;

public class OrValidator extends Validator {
    protected ArrayList<Validator> validators = new ArrayList<Validator>();
    protected String errorMsg = null;

    public OrValidator() {
        super(null);
    }

    public OrValidator(String error) {
        super(error);
    }

    public void addValidator(Validator v) {
        if (null != v) validators.add(v);
    }

    public int getCount() {
        return validators.size();
    }

    @Override
    public String getErrorMessage(EditText e) {
        return null != errorMsg ? errorMsg : errorMessage;
    }

    @Override
    public boolean check(EditText e) {
        for (Validator v : validators) {
            try {
                if (v.check(e)) return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            errorMsg = v.getErrorMessage(e);
        }

        return false;
    }
}
