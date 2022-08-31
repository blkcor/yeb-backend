package com.chen.server.controller;


import com.chen.server.pojo.Menu;
import com.chen.server.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {
    @Resource
    private MenuService menuService;

    @ApiOperation(value = "通过用户ID查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByAdminId() {
        return menuService.getMenusByAdminId();
    }
}

