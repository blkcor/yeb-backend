package com.chen.server.service;

import com.chen.server.common.RespBean;
import com.chen.server.common.RespPageBean;
import com.chen.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 获取所有的员工
     *
     * @param current
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmployeeByPage(Integer current, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取当前工号的最大值  用来下一次给员工指定工号的使用进行 +1 保证不重复
     *
     * @return
     */
    RespBean getMaxWorkID();

    /**
     * 添加员工
     *
     * @param employee
     * @return
     */
    RespBean addEmp(Employee employee);


    /**
     * 查询员工信息
     *
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);
}
