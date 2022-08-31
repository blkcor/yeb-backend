package com.chen.server.controller;


import com.chen.server.common.RespBean;
import com.chen.server.pojo.Position;
import com.chen.server.service.PositionService;
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
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Resource
    private PositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public RespBean getPositionInfo() {
        return RespBean.success("获取信息成功!", positionService.list());
    }

    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        boolean res = positionService.save(position);
        if (res) {
            return RespBean.success("添加职位信息成功");
        }
        return RespBean.error("添加失败,请仔细检查!");

    }

    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position) {
        boolean res = positionService.updateById(position);
        if (res) {
            return RespBean.success("更新职位信息成功");
        }
        return RespBean.error("更新失败,请仔细检查!");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable("id") Integer id) {
        boolean res = positionService.removeById(id);
        if (res) {
            return RespBean.success("删除职位信息成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public RespBean deletePositionBatch(Integer[] ids) {
        boolean res = positionService.removeBatchByIds(Arrays.asList(ids));
        if (res) {
            return RespBean.success("批量删除职位信息成功");
        }
        return RespBean.error("批量删除失败");
    }
}

