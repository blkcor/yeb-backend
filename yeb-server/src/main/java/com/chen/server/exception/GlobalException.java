package com.chen.server.exception;

import com.chen.server.common.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author: blkcor
 * @DATE: 2022/3/27  1:02
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * sql异常统一处理
     * 捕获sql及其子类异常
     *
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public RespBean sqlExceptionHandler(SQLException exception) {
        if (exception instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据,操作失败!");
        }
        return RespBean.error("数据库异常!");
    }
}
