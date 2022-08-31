package com.chen.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.server.common.RespBean;

import com.chen.server.pojo.Menu;
import com.chen.server.pojo.MenuRole;
import com.chen.server.pojo.Role;
import com.chen.server.service.MenuRoleService;
import com.chen.server.service.MenuService;
import com.chen.server.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组控制器
 *
 * @author: blkcor
 * @DATE: 2022/3/27  11:01
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@RestController
@RequestMapping("/system/basic/permiss")
@SuppressWarnings("all")
public class PermissController {
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private MenuRoleService menuRoleService;
    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * =========================角色相关==================================
     **/

    @ApiOperation(value = "获取全部角色")
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if (!role.getName().startsWith(ROLE_PREFIX)) {
            role.setName(ROLE_PREFIX.concat(role.getName()));
        }
        boolean res = roleService.save(role);
        if (res) {
            return RespBean.success("添加角色成功");
        }
        return RespBean.error("添加角色失败");
    }


    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable("rid") Integer rid) {
        boolean res = roleService.removeById(rid);
        if (res) {
            return RespBean.success("删除角色成功");
        }
        return RespBean.error("删除失败");
    }

    /**
     * =========================菜单相关==================================
     **/
    @ApiOperation(value = "获取全部菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色ID查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMenusIdsByRid(@PathVariable("rid") Integer rid) {
        List<MenuRole> menusRoleList = menuRoleService.list(new QueryWrapper<MenuRole>().lambda().eq(MenuRole::getRid,rid));
        return menusRoleList.stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        return menuRoleService.updateMenuRole(rid, mids);

    }
}
