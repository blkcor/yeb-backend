package com.chen.server.controller;

import com.chen.server.common.RespBean;

import com.chen.server.dto.req.AdminLogin;
import com.chen.server.pojo.Admin;
import com.chen.server.service.AdminService;
import com.chen.server.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录请求
 *
 * @author: blkcor
 * @DATE: 2022/5/23  13:38
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Api("LoginController")
@RestController
public class LoginController {
    @Resource
    private AdminService adminService;
    @Resource
    private RoleService roleService;

    @ApiOperation("登录之后返回Token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLogin adminLogin, HttpServletRequest request) {
        System.out.println("用户"+adminLogin.getUsername()+"正在登陆...");
        return adminService.login(adminLogin, request);
    }

    @ApiOperation("获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        //设置密码为不可见
        Admin admin = adminService.getAdminByUserName(username);
        admin.setRoleList(adminService.getRoleList(admin.getId()));
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功!");
    }
}
