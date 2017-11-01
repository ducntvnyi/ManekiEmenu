package com.qslib.filestorage.helpers;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Dang on 12/22/2015.
 */
public enum OrderType {
    NAME,
    /**
     * Last modified is the first
     */
    DATE,
    /**
     * Smaller size will be in the first place
     */
    SIZE;

    public Comparator<File> getComparator() {
        try {
            switch (ordinal()) {
                case 0: // name
                    return (lhs, rhs) -> {
                        try {
                            return lhs.getName().compareTo(rhs.getName());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        return 0;
                    };
                case 1: // date
                    return (lhs, rhs) -> {
                        try {
                            return (int) (rhs.lastModified() - lhs.lastModified());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        return 0;
                    };
                case 2: // size
                    return (lhs, rhs) -> {
                        try {
                            return (int) (lhs.length() - rhs.length());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        return 0;
                    };
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
