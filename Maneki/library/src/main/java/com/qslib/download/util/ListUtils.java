package com.qslib.download.util;

import java.util.List;

/**
 * Created by Dang
 */
public class ListUtils {
    public static final boolean isEmpty(List list) {
        if (list != null && list.size() > 0) {
            return false;
        }

        return true;
    }
}
