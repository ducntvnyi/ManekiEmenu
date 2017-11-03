package com.qslib.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringUtils {
    /**
     * use to display list to string, for example, address, city, state
     *
     * @param strings
     * @return
     */
    public static String joinText(String... strings) {
        return joinText(", ", strings);
    }

    /**
     * use to display list to string, for example, address, city, state
     *
     * @param strings
     * @return
     */
    public static String joinText(String joinCharacter, String... strings) {
        try {
            if (strings == null || strings.length <= 0) return "";

            List<String> stringList = new ArrayList<>();
            for (String s : strings) if (!StringUtils.isEmpty(s)) stringList.add(s);
            if (stringList == null || stringList.size() <= 0) return "";

            return TextUtils.join(joinCharacter, stringList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @param strings
     * @return
     */
    public static String joinText(List<String> strings) {
        return joinText(", ", strings);
    }

    /**
     * @param joinCharacter
     * @param strings
     * @return
     */
    public static String joinText(String joinCharacter, List<String> strings) {
        try {
            if (strings == null || strings.size() <= 0) return "";

            List<String> stringList = new ArrayList<>();
            for (String s : strings) if (!StringUtils.isEmpty(s)) stringList.add(s);
            if (stringList == null || stringList.size() <= 0) return "";

            return TextUtils.join(joinCharacter, stringList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * check string empty, null...
     *
     * @param value
     * @return true or false
     */
    @SuppressLint("DefaultLocale")
    public static boolean isEmpty(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("") || value.equalsIgnoreCase(" ") || TextUtils.isEmpty(value)
                    || value.equalsIgnoreCase("null")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * get random string
     *
     * @param sizeOfRandomString
     * @return
     */
    public static String getRandomString(final int sizeOfRandomString) {
        try {
            final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
            final Random random = new Random();
            final StringBuilder sb = new StringBuilder(sizeOfRandomString);
            for (int i = 0; i < sizeOfRandomString; ++i) {
                sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
