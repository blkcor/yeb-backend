package com.chen.server.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录实体类
 *
 * @author: blkcor
 * @DATE: 2022/3/25  19:35
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户登录实体类", description = "用于映射用户登录的用户名、密码、验证码信息")
public class AdminLogin implements Serializable {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
