package com.chen.server.mapper;

import com.chen.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量更新角色菜单
     *
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecords(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
