package com.example.springbootccpms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootccpms.common.Result;
import com.example.springbootccpms.entity.Station;
import com.example.springbootccpms.entity.dto.IdlePileDto;
import com.example.springbootccpms.mapper.PileMapper;
import com.example.springbootccpms.mapper.StationMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/station")
public class StationController extends BaseController{

    @Resource
    private StationMapper stationMapper;

    @Resource
    private PileMapper pileMapper;

    @PostMapping
    public Result<?> save(@RequestBody Station station) {
        stationMapper.insert(station);

        //修改所属充电桩状态
        if((station.getState()).equals("运行中")){
            pileMapper.updatePileStateByStationId1(station.getId());
        }
        if((station.getState()).equals("维修中")||(station.getState()).equals("禁用")){
            pileMapper.updatePileStateByStationId2(station.getId());
        }

        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Station station) {
        stationMapper.updateById(station);

        //修改所属充电桩状态
        if((station.getState()).equals("运行中")){
            pileMapper.updatePileStateByStationId1(station.getId());
        }
        if((station.getState()).equals("维修中")||(station.getState()).equals("禁用")){
            pileMapper.updatePileStateByStationId2(station.getId());
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Integer id) {
        //删除指定id充电站信息
        stationMapper.deleteById(id);
        //删除指定id充电站下属充电桩的信息
        stationMapper.deletePileByStationId(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(stationMapper.selectById(id));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(stationMapper.selectList(null));
    }

    @GetMapping("/countIdlePile")
    public Result<?> countAddress() {
//        User user = getUser(); // 当前登录的用户信息
        List<IdlePileDto> idlePileData=stationMapper.countIdlePile();
        //根据id查询站名插入数据中
        for(IdlePileDto idlePile:idlePileData){
            idlePile.setStationName(stationMapper.selectStationNameById(idlePile.getStationId()));
        }
        return Result.success(idlePileData);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Station> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Station::getStationName, search);
        }
        Page<Station> StationPage = stationMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(StationPage);
    }
}
