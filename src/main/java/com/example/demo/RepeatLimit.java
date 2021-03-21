package com.example.demo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shixin
 * @date 2021/3/21
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatLimit {
    /**
     * 重复提交限制的key
     *
     * @return
     */
    String key() default "";

    /**
     * 重复提交间隔时间(秒)，默认10秒
     *
     * @return
     */
    long interval() default 10;
}
