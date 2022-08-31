package com.chen.server.controller;


import com.chen.server.common.RespBean;
import com.chen.server.pojo.Joblevel;
import com.chen.server.service.JoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {
    @Resource
    private JoblevelService jobLevelService;


    @ApiOperation(value = "获取所有职称信息")
    @GetMapping("/")
    public RespBean getJobLevelInfo() {
        return RespBean.success("获取信息成功!", jobLevelService.list());
    }

    @ApiOperation(value = "添加职称信息")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel) {
        joblevel.setCreateDate(LocalDateTime.now());
        boolean res = jobLevelService.save(joblevel);
        if (res) {
            return RespBean.success("添加职称信息成功");
        }
        return RespBean.error("添加失败,请仔细检查!");

    }

    @ApiOperation(value = "更新职称信息")
    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody Joblevel joblevel) {
        boolean res = jobLevelService.updateById(joblevel);
        if (res) {
            return RespBean.success("更新职称信息成功");
        }
        return RespBean.error("更新失败,请仔细检查!");
    }

    @ApiOperation(value = "删除职称信息")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable("id") Integer id) {
        boolean res = jobLevelService.removeById(id);
        if (res) {
            return RespBean.success("删除职称信息成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职称信息")
    @DeleteMapping("/")
    public RespBean deleteJobLevelBatch(Integer[] ids) {
        boolean res = jobLevelService.removeBatchByIds(Arrays.asList(ids));
        if (res) {
            return RespBean.success("批量删除职称信息成功");
        }
        return RespBean.error("批量删除失败");
    }

}

