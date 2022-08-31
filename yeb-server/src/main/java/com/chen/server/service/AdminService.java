package com.chen.server.service;

import com.chen.server.common.RespBean;
import com.chen.server.dto.req.AdminLogin;
import com.chen.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chen.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface AdminService extends IService<Admin> {
    /**
     * 登录过后返回Login
     *
     * @param adminLogin
     * @param request
     * @return
     */
    RespBean login(AdminLogin adminLogin, HttpServletRequest request);


    /**
     * 通过用户名查找用户信息
     *
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 根据用户id获取用户的角色列表
     *
     * @param adminId
     * @return
     */
    List<Role> getRoleList(Integer adminId);

    /**
     * 获取所有的操作员
     *
     * @param keyWord
     * @return
     */
    List<Admin> getAllAdmins(String keyWord);
}
