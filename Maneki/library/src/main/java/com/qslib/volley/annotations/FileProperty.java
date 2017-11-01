package com.qslib.volley.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Dang on 5/16/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FileProperty {
    /**
     * @return the desired name of the column representing the field
     */
    String value();

    boolean treatNullAsDefault() default false;

    boolean readonly() default false;
}
