package com.chen.server.utils;

import com.chen.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 操作员工具类
 *
 * @author: blkcor
 * @DATE: 2022/3/27  17:26
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
public class AdminUtils {
    public static Admin getCurrentAdmin() {
        return ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
