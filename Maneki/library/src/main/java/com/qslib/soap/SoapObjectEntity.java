package com.qslib.soap;

import java.io.Serializable;

/**
 * Created by dangpp on 12/30/2016.
 */

public class SoapObjectEntity implements Serializable {
    private String key;
    private byte[] value;

    public SoapObjectEntity(String key, byte[] value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SoapObjectEntity{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
