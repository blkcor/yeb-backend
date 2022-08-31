package com.chen.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.chen.server.common.RespBean;
import com.chen.server.common.RespPageBean;
import com.chen.server.pojo.*;
import com.chen.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/employee/basic/")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;
    @Resource
    private PoliticsStatusService politicsStatusService;
    @Resource
    private JoblevelService joblevelService;
    @Resource
    private NationService nationService;
    @Resource
    private PositionService positionService;
    @Resource
    private DepartmentService departmentService;

    @ApiOperation("分页查询所有的员工")
    @GetMapping("/")
    public RespPageBean employeePage(@RequestParam(name = "current", defaultValue = "1") Integer current,
                                     @RequestParam(name = "size", defaultValue = "10") Integer size,
                                     Employee employee, LocalDate[] beginDateScope) {
        return employeeService.getEmployeeByPage(current, size, employee, beginDateScope);
    }

    @ApiOperation("获取所有的政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getPoliticsStatus() {
        return politicsStatusService.list();
    }

    @ApiOperation("获取所有的职称")
    @GetMapping("/jobLevels")
    public List<Joblevel> getJobLevels() {
        return joblevelService.list();
    }

    @ApiOperation("获取所有的民族")
    @GetMapping("/nation")
    public List<Nation> getNation() {
        return nationService.list();
    }

    @ApiOperation("获取所有的职位")
    @GetMapping("/position")
    public List<Position> getPosition() {
        return positionService.list();
    }

    /**
     * 查询部门以及子部门 ==> 我们在Mybatis的resultMap层进行了递归查询
     *
     * @return
     */
    @ApiOperation("获取所有的部门")
    @GetMapping("/department")
    public List<Department> getDepartment() {
        return departmentService.getDepartments();
    }

    @ApiOperation("获取工号")
    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkID() {
        return employeeService.getMaxWorkID();
    }

    @ApiOperation("添加员工")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        return employeeService.addEmp(employee);
    }

    @ApiOperation("更新员工")
    @PutMapping("/updateEmp")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/{id}")
    public RespBean updateEmp(@PathVariable("id") Integer empId) {
        if (employeeService.removeById(empId)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation("导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> employee = employeeService.getEmployee(null);
        ExportParams exportParams = new ExportParams("员工表", "sheet0", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Employee.class, employee);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工表.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ApiOperation("导入员工数据")
    @PostMapping(value = "/import")
    public RespBean importEmployee(MultipartFile file) {
        ImportParams importParams = new ImportParams();
        //去掉标题行
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        //查询所有民族
        List<Nation> nationList = nationService.list();
        //查询所有政治面貌
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        //查询所有部门
        List<Department> departmentList = departmentService.list();
        //查询所有职称
        List<Joblevel> joblevelList = joblevelService.list();
        //查询所有职位
        List<Position> positionList = positionService.list();
        //导入工作
        try {
            List<Employee> employees = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, importParams);
            employees.forEach(employee -> {
                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setJobLevelId(joblevelList.get(joblevelList.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                employee.setPosId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
            });
            //保存到数据库
            if (employeeService.saveBatch(employees)) {
                return RespBean.success("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败");
    }

}

