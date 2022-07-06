package com.example.springbootccpms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootccpms.common.Result;
import com.example.springbootccpms.entity.Pile;
import com.example.springbootccpms.entity.Role;
import com.example.springbootccpms.entity.User;
import com.example.springbootccpms.entity.UserRole;
import com.example.springbootccpms.entity.dto.PileStateTotalDto;
import com.example.springbootccpms.mapper.PileMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/pile")
public class PileController extends BaseController{
    @Resource
    private PileMapper pileMapper;

    @PostMapping
    public Result<?> save(@RequestBody Pile pile) {
        pileMapper.insert(pile);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Pile pile) {
        pileMapper.updateById(pile);
        return Result.success();
    }

    @PutMapping("/pileAbnormal/{id}")
    public Result<?> updatePileState(@PathVariable Integer id) {
        pileMapper.updateAbnormalPileById(id);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        pileMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(pileMapper.selectById(id));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(pileMapper.selectList(null));
    }

    @GetMapping("/idleTotal")
    public Result<?> idlePile() {
        return Result.success((pileMapper.selectIdlePileQuantity()));
    }

    @GetMapping("/totalPile")
    public Result<?> totalPile() {
        PileStateTotalDto pileStateTotalDto=new PileStateTotalDto();

        pileStateTotalDto.setTotalPile(pileMapper.selectTotalPileQuantity());
        pileStateTotalDto.setTotalIdlePile(pileMapper.selectIdlePileQuantity());
        pileStateTotalDto.setTotalReservedPile(pileMapper.selectTotalReservedPileQuantity());
        pileStateTotalDto.setTotalChargingPile(pileMapper.selectTotalChargingPileQuantity());
        pileStateTotalDto.setTotalRepairPile(pileMapper.selectTotalRepairPileQuantity());

        return Result.success(pileStateTotalDto);
    }

    @GetMapping("/idle")
    public Result<?> idlePileTotal() {

        return Result.success(pileMapper.selectIdlePileList());
    }


    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Pile> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Pile::getPileName, search);
        }
        Page<Pile> PilePage = pileMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(PilePage);
    }
}
