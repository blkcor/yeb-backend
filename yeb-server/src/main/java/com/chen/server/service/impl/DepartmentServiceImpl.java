package com.chen.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.server.common.RespBean;
import com.chen.server.pojo.Department;
import com.chen.server.mapper.DepartmentMapper;
import com.chen.server.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDepartments() {
        return departmentMapper.getDepartments(-1);
    }

    @Override
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        if (department.getResult() > 0) {
            return RespBean.success("添加成功!", department);
        }
        return RespBean.error("添加失败!");
    }

    @Override
    public RespBean deleteDepartment(Integer id) {
        Department deleteDepartment = departmentMapper.selectById(id);
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        Integer result = department.getResult();
        deleteDepartment.setResult(result);
        if (result == -2) {
            return RespBean.error("删除失败!该部门有关联的子部门");
        }
        if (result == -1) {
            return RespBean.error("删除失败!该部门下有关联的员工");
        }
        return RespBean.success("删除成功!", deleteDepartment);
    }
}
