package com.qslib.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;

import com.qslib.edittext.validators.AlphaNumericValidator;
import com.qslib.edittext.validators.AlphaValidator;
import com.qslib.edittext.validators.CreditCardValidator;
import com.qslib.edittext.validators.DomainValidator;
import com.qslib.edittext.validators.EmailValidator;
import com.qslib.edittext.validators.IpValidator;
import com.qslib.edittext.validators.NumericValidator;
import com.qslib.edittext.validators.OrValidator;
import com.qslib.edittext.validators.PhoneValidator;
import com.qslib.edittext.validators.RegExpValidator;
import com.qslib.edittext.validators.Validator;
import com.qslib.edittext.validators.WebUrlValidator;
import com.qslib.library.R;
import com.qslib.util.StringUtils;

import java.util.ArrayList;

public class ExtEditText extends EditText {
    private static final int TEST_REGEXP = 0x0001;
    private static final int TEST_NUMERIC = 0x0002;
    private static final int TEST_ALPHA = 0x0004;
    private static final int TEST_ALPHANUMERIC = 0x0008;
    private static final int TEST_EMAIL = 0x0010;
    private static final int TEST_CREDITCARD = 0x0020;
    private static final int TEST_PHONE = 0x0040;
    private static final int TEST_DOMAINNAME = 0x0080;
    private static final int TEST_IPADDRESS = 0x0100;
    private static final int TEST_WEBURL = 0x0200;
    private static final int TEST_ALL = 0x07ff;
    private static final int TEST_NOCHECK = 0;

    /**
     * @var Validators heap
     */
    private boolean emptyAllowed = false;
    private String errorString = null;
    private String emptyErrorString = null;
    protected ArrayList<Validator> validators = new ArrayList<Validator>();

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public ExtEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ExtEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initControl(context, attrs);
    }

    /**
     * Init control params
     *
     * @param context
     * @param attrs
     */
    protected void initControl(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtEditText);
        errorString = typedArray.getString(R.styleable.ExtEditText_errorString);
        emptyErrorString = typedArray.getString(R.styleable.ExtEditText_emptyErrorString);
        emptyAllowed = typedArray.getBoolean(R.styleable.ExtEditText_isEmpty, false);

        if (StringUtils.isEmpty(emptyErrorString))
            emptyErrorString = getResources().getString(R.string.error_field_must_not_be_empty);

        addValidator(
                typedArray.getInt(R.styleable.ExtEditText_validate, TEST_REGEXP | TEST_NOCHECK),
                errorString,
                typedArray.getString(R.styleable.ExtEditText_validatorRegexp));
        typedArray.recycle();
    }

    /**
     * Add validator by code
     *
     * @param code
     * @param error
     * @param regExp
     * @return
     */
    public boolean addValidator(int code, String error, String regExp) {
        if (0 == (code | TEST_ALL))
            return false;

        OrValidator v = new OrValidator();
        if ((0 == code || (code & TEST_REGEXP) != 0) && null != regExp) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_regexp_not_valid);
            v.addValidator(new RegExpValidator(regExp, error));
        }

        if ((code & TEST_NUMERIC) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_this_field_cannot_contain_special_character);
            v.addValidator(new NumericValidator(error));
        }

        if ((code & TEST_ALPHA) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_only_standard_letters_are_allowed);
            v.addValidator(new AlphaValidator(error));
        }

        if ((code & TEST_ALPHANUMERIC) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_this_field_cannot_contain_special_character);
            v.addValidator(new AlphaNumericValidator(error));
        }

        if ((code & TEST_EMAIL) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_email_address_not_valid);
            v.addValidator(new EmailValidator(error));
        }

        if ((code & TEST_CREDITCARD) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_creditcard_number_not_valid);
            v.addValidator(new CreditCardValidator(error));
        }

        if ((code & TEST_PHONE) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_phone_not_valid);
            v.addValidator(new PhoneValidator(error));
        }

        if ((code & TEST_DOMAINNAME) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_domain_not_valid);
            v.addValidator(new DomainValidator(error));
        }

        if ((code & TEST_IPADDRESS) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_ip_not_valid);
            v.addValidator(new IpValidator(error));
        }

        if ((code & TEST_WEBURL) != 0) {
            if (StringUtils.isEmpty(error))
                error = getResources().getString(R.string.error_url_not_valid);
            v.addValidator(new WebUrlValidator(error));
        }

        if (0 < v.getCount()) validators.add(v);

        return true;
    }

    /**
     * Add validator object
     *
     * @param v
     */
    public void addValidator(Validator v) {
        if (null != v) validators.add(v);
    }

    /**
     * Check validate
     *
     * @return
     */
    public boolean isValid() {
        if (StringUtils.isEmpty(getText().toString())) {
            if (emptyAllowed) {
                return true;
            } else {
                setError(emptyErrorString);
                requestFocus();
                return false;
            }
        }

        boolean b = false;
        if (null != validators) {
            for (Validator v : validators) {
                try {
                    b = v.check(this);
                } catch (Exception e) {
                    e.printStackTrace();
                    b = false;
                }

                if (!b) {
                    String error = v.getErrorMessage(this);
                    if (StringUtils.isEmpty(error)) error = errorString;
                    if (!StringUtils.isEmpty(error)) {
                        setError(error);
                        requestFocus();
                    }

                    return false;
                }
            }
        }

        return true;
    }
}
