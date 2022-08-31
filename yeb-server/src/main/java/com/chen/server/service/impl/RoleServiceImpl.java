package com.chen.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.server.common.RespBean;
import com.chen.server.mapper.AdminMapper;
import com.chen.server.mapper.AdminRoleMapper;
import com.chen.server.pojo.AdminRole;
import com.chen.server.pojo.Role;
import com.chen.server.mapper.RoleMapper;
import com.chen.server.service.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private AdminRoleMapper adminRoleMapper;

    /**
     * 涉及两个及以上的操作 开启声明式事务
     *
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public RespBean updateAdminRoles(Integer adminId, Integer[] rids) {
        //删除原记录  操作员的原角色
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().lambda().eq(AdminRole::getAdminId, adminId));
        Integer res = roleMapper.updateAdminRoles(adminId, rids);
        if (res == rids.length) {
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
}