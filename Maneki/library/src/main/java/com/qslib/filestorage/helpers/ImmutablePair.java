package com.qslib.filestorage.helpers;

import java.io.Serializable;

/**
 * Created by Dang on 12/22/2015.
 */
public class ImmutablePair<T, S> implements Serializable {
    private static final long serialVersionUID = 40;

    public final T element1;
    public final S element2;

    public ImmutablePair() {
        this.element1 = null;
        this.element2 = null;
    }

    public ImmutablePair(T element1, S element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    @Override
    public boolean equals(Object object) {
        try {
            if (!(object instanceof ImmutablePair)) {
                return false;
            }

            Object object1 = ((ImmutablePair<?, ?>) object).element1;
            Object object2 = ((ImmutablePair<?, ?>) object).element2;

            return (element1 != null && element1.equals(object1)) && (element2 != null && element2.equals(object2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public int hashCode() {
        try {
            return (element1 != null ? element1.hashCode() : 0) << 16 + (element2 != null ? element2.hashCode() : 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}