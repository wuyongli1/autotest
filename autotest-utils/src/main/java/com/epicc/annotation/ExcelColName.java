package com.epicc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author 刘飞
* @version 创建时间：2018年3月12日 下午4:40:59
* @Description 用于匹配Excel表中的列(vlaue值必须是Excel文件中第一行的列名)
*/
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColName {
    String value() default "";
    boolean IsRequired() default false;
}