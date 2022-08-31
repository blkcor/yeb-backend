package com.chen.server.controller;


import com.chen.server.common.RespBean;
import com.chen.server.pojo.Department;
import com.chen.server.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
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
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/")
    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }

    @ApiOperation(value = "添加一个部门")
    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }


    @ApiOperation(value = "删除一个部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDepartment(@PathVariable("id") Integer id) {
        return departmentService.deleteDepartment(id);
    }
}

