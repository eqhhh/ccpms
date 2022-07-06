package com.example.springbootccpms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootccpms.common.Result;
import com.example.springbootccpms.entity.Permission;
import com.example.springbootccpms.mapper.PermissionMapper;
import com.example.springbootccpms.mapper.RoleMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Resource
    PermissionMapper permissionMapper;

    @Resource
    RoleMapper roleMapper;

    @PostMapping
    public Result<?> save(@RequestBody Permission Permission) {
        permissionMapper.insert(Permission);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Permission Permission) {
        permissionMapper.updateById(Permission);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Integer id) {
        permissionMapper.deleteById(id);
        roleMapper.deleteRolePermissionByPermissionId(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(permissionMapper.selectById(id));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(permissionMapper.selectList(null));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Permission> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Permission::getName, search);
        }
        Page<Permission> PermissionPage = permissionMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(PermissionPage);
    }
}

