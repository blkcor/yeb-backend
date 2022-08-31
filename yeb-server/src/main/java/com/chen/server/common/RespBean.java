package com.chen.server.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 *
 * @author: blkcor
 * @DATE: 2022/3/25  19:28
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    /**
     * 状态码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回对象
     */
    private Object object;

    /**
     * 成功请求
     *
     * @param message
     * @return
     */
    public static RespBean success(String message) {
        return new RespBean("200", message, null);
    }

    /**
     * 成功请求
     *
     * @param message
     * @param obj
     * @return
     */
    public static RespBean success(String message, Object obj) {
        return new RespBean("200", message, obj);
    }

    /**
     * 失败请求
     *
     * @param message
     * @return
     */
    public static RespBean error(String message) {
        return new RespBean("500", message, null);
    }

    /**
     * 失败请求
     *
     * @param message
     * @return
     */
    public static RespBean error(String message, Object obj) {
        return new RespBean("500", message, obj);
    }

}
