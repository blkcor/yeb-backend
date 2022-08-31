package com.chen.server.service;

import com.chen.server.common.RespBean;
import com.chen.server.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface RoleService extends IService<Role> {

    /**
     * 更新操作员角色
     *
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRoles(Integer adminId, Integer[] rids);
}
