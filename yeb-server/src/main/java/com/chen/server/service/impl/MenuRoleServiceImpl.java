package com.chen.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.server.common.RespBean;
import com.chen.server.pojo.MenuRole;
import com.chen.server.mapper.MenuRoleMapper;
import com.chen.server.service.MenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
@Service
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

    @Resource
    private MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        //清空当前用户的菜单权限
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().lambda().eq(MenuRole::getRid, rid));
        if (null == mids || mids.length == 0) {
            return RespBean.success("更新成功");
        }
        Integer res = menuRoleMapper.insertRecords(rid, mids);
        if (res == mids.length) {
            return RespBean.success("更新成功", res);
        }
        return RespBean.error("更新失败,请检查参数类型以及参数的长度是否匹配");

    }
}
