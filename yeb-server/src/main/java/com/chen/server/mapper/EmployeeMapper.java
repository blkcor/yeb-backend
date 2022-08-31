package com.chen.server.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.server.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 获取所有员工（分页数据）
     * 将page传入 对查询结果自动进行分页
     *
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 获取当前的最大工号
     *
     * @return
     */
    String getMaxWorkID();

    /**
     * 根据id查询员工数据
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);
}
