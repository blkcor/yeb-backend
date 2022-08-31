package com.chen.server.service;

import com.chen.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据用户id查询菜单列表
     *
     * @return
     */
    List<Menu> getMenusByAdminId();


    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusByRole();

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
