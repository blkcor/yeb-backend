package com.chen.server.mapper;

import com.chen.server.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色列表
     *
     * @param adminId
     * @return
     */
    List<Role> getRoleList(Integer adminId);

    /**
     * 更新操作员的角色
     *
     * @param adminId
     * @param rids
     * @return
     */
    Integer updateAdminRoles(@Param("adminId") Integer adminId,@Param("rids") Integer[] rids);
}
