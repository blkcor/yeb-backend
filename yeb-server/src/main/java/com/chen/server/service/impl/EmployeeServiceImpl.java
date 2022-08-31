package com.chen.server.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.server.common.RespBean;
import com.chen.server.common.RespPageBean;
import com.chen.server.mapper.EmployeeMapper;
import com.chen.server.pojo.Employee;
import com.chen.server.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public RespPageBean getEmployeeByPage(Integer current, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //得到分页对象
        Page<Employee> page = new Page<>(current, size, true);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean pageBean = new RespPageBean(employeeByPage.getTotal(), employeeByPage.getRecords());
        return pageBean;
    }

    @Override
    public RespBean getMaxWorkID() {
        return RespBean.success("获取工号成功", String.format("%08d", Integer.parseInt(employeeMapper.getMaxWorkID()) + 1));
    }

    @Override
    public RespBean addEmp(Employee employee) {
        //设置工号
        employee.setWorkID(this.getMaxWorkID().getObject().toString());
        //处理合同期限
        LocalDate begin = employee.getBeginContract().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = employee.getEndContract().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //算出时间间隔
        long days = begin.until(end, ChronoUnit.DAYS);
        //指定格式 保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365.00)));

        //数据处理完毕  插入数据库
        if (1 == employeeMapper.insert(employee)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @Override
    public List<Employee> getEmployee(Integer id) {
        return employeeMapper.getEmployee(id);
    }
}
