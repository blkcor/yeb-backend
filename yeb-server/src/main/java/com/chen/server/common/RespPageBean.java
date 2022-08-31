package com.chen.server.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页通用返回对象
 *
 * @author: blkcor
 * @DATE: 2022/3/27  22:26
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPageBean {
    /**
     * 记录总条数
     */
    private Long total;
    /**
     * 分页集合对象
     */
    private List<?> data;
}
