package com.chen.server.controller;


import com.chen.server.common.RespBean;
import com.chen.server.pojo.Admin;
import com.chen.server.pojo.Role;
import com.chen.server.service.AdminService;
import com.chen.server.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private RoleService roleService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keyWord) {
        return adminService.getAllAdmins(keyWord);
    }

    @ApiOperation("更新操作员")
    @PutMapping("/")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        boolean res = adminService.updateById(admin);
        if (res) {
            return RespBean.success("更新成功", admin);
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation("删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable("id") Integer id) {
        boolean res = adminService.removeById(id);
        if (res) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("获取所有角色")
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.list();
    }

    @ApiOperation("更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRoles(Integer adminId, Integer[] rids) {
        return roleService.updateAdminRoles(adminId, rids);
    }
}

