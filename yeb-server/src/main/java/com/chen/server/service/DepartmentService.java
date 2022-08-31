package com.chen.server.service;

import com.chen.server.common.RespBean;
import com.chen.server.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 获取所有部门信息
     *
     * @return
     */
    List<Department> getDepartments();


    /**
     * 添加一个部门
     *
     * @param department
     * @return
     */
    RespBean addDepartment(Department department);

    /**
     * 删除一个部门
     *
     * @param id
     * @return
     */
    RespBean deleteDepartment(Integer id);
}
