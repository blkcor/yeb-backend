package com.chen.server.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 全局日期转化
 *
 * @author: blkcor
 * @DATE: 2022/3/27  22:29
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Component
public class DateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        //指定格式化日期转化
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
