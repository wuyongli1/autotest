package com.epicc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* @author 刘飞
* @version 创建时间：2018年3月12日 下午4:39:48
* @Description 用于匹配Excel文件中的sheet表名(注解值必须是Excel文件中sheet表名)
*/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSheetName {
     String value() default "";
}