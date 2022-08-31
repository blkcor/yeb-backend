package com.chen.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.chen.server.pojo.Menu;
import com.chen.server.mapper.MenuMapper;
import com.chen.server.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.server.utils.AdminUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Menu> getMenusByAdminId() {
        //去security的上下文中去拿用户的ID
        Integer adminId = (AdminUtils.getCurrentAdmin()).getId();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        if (CollectionUtils.isEmpty(menus)) {
            //redis不存在 我们去数据库中拿
            List<Menu> tMenus = menuMapper.getMenusByAdminId(adminId);
            //存入redis中
            valueOperations.set("menu_" + adminId, tMenus);
            return tMenus;
        } else {
            //redis中存在 我们直接返回redis中的对象
            return menus;
        }
    }

    @Override
    public List<Menu> getMenusByRole() {
        return menuMapper.getMenusByRole();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
