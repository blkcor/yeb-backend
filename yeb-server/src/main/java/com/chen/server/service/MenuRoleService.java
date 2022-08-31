package com.chen.server.service;

import com.chen.server.common.RespBean;
import com.chen.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface MenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     *
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
