package me.caden2k3.oneclass.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 2019-03-31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FXMLChild {
    String path() default "";
}
