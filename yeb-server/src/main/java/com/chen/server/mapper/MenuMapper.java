package com.chen.server.mapper;

import com.chen.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id查询菜单列表
     *
     * @param id
     * @return
     */
    List<Menu> getMenusByAdminId(@Param("id") Integer id);

    /**
     * 根据角色获取菜单列表
     *
     * @return
     */
    List<Menu> getMenusByRole();

    /**
     * 获取所有菜单信息
     *
     * @return
     */
    List<Menu> getAllMenus();
}
