package com.qslib.util;

import android.text.InputType;
import android.widget.EditText;

/**
 * Created by Administrator on 12/01/2017.
 */

public class PasswordViewUtils {
    public static void setShowOrHidePassword(EditText edtPassword) {
        try {
            if (edtPassword.getInputType() == (InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            edtPassword.setSelection(edtPassword.getText().length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
