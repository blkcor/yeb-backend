package com.chen.server.mapper;

import com.chen.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.server.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 不能获取当前登录过的admin
     * @param id 当前登录的操作员的id
     * @param keyWord 关键字  模糊查询使用
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keyWord") String keyWord);
}
