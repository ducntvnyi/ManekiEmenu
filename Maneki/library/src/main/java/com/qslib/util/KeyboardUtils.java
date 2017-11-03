package com.qslib.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.qslib.customview.edittext.ExtEditText;

public class KeyboardUtils {
    /**
     * don't show keyboard for activity
     *
     * @param activity
     */
    public static void dontShowKeyboardActivity(Activity activity) {
        try {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            hideSoftKeyboard(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hide keyboard for EditText
     *
     * @param context
     * @param editText
     */
    public static void hideKeyboard(Context context, EditText editText) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hide keyboard for view
     *
     * @param view
     * @param activity
     */
    public static void hideKeyboard(View view, final Activity activity) {
        try {
            // Set up touch listener for non-text box views to hide keyboard.
            if (!(view instanceof EditText) || !(view instanceof ExtEditText)) {
                view.setOnTouchListener((v, event) -> {
                    hideSoftKeyboard(activity);
                    return false;
                });
            }

            // If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View innerView = ((ViewGroup) view).getChildAt(i);
                    hideKeyboard(innerView, activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hide keyboard for activity
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (activity != null && activity.getCurrentFocus() != null
                        && activity.getCurrentFocus().getWindowToken() != null)
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set selection at end of View
     *
     * @param etEditText
     */
    private static void setSelectionText(final TextView etEditText) {
        try {
            etEditText.post(() -> {
                try {
                    String text = etEditText.getText().toString();
                    if (!StringUtils.isEmpty(text)
                            && etEditText instanceof EditText) {
                        ((EditText) etEditText).setSelection(text.length());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set focus to EditText
     *
     * @param etEditText
     */
    public static void setFocusToEditText(Context context,
                                          TextView etEditText) {
        try {
            etEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etEditText, InputMethodManager.SHOW_IMPLICIT);
            // set text selection
            setSelectionText(etEditText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * set focus to EditText on dialog
     *
     * @param activity
     * @param dialog
     * @param etEditText
     */
    public static void setFocusToEditTextDialog(final Activity activity,
                                                Dialog dialog, final TextView etEditText) {
        try {
            etEditText.requestFocus();
            dialog.setOnShowListener(dialogInterface -> {
                try {
                    InputMethodManager imm = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(etEditText,
                            InputMethodManager.SHOW_IMPLICIT);
                    // set text selection
                    setSelectionText(etEditText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
