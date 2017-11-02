package com.qslib.soap;

/**
 * Created by Hungnd on 11/2/17.
 */

public class SoapRequest {

    private String propertyName;
    private Object value;

    public SoapRequest(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SoapRequest{" +
                "propertyName='" + propertyName + '\'' +
                ", value=" + value +
                '}';
    }
}
