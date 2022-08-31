package com.chen.server.mapper;

import com.chen.server.pojo.Department;
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
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门信息
     *
     * @param departmentId
     * @return
     */
    List<Department> getDepartments(@Param("pid") Integer departmentId);

    /**
     * 添加一个部门
     *
     * @param department
     */
    void addDepartment(Department department);

    /**
     * 删除一个部门
     * 传入实体类 方便存储过程做映射
     *
     * @param department
     */
    void deleteDepartment(Department department);
}
