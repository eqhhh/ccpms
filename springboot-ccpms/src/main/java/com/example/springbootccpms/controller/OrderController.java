package com.example.springbootccpms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootccpms.common.Result;
import com.example.springbootccpms.entity.Order;
import com.example.springbootccpms.mapper.OrderMapper;
import com.example.springbootccpms.mapper.PileMapper;
import com.example.springbootccpms.mapper.StationMapper;
import com.example.springbootccpms.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Resource
    OrderMapper orderMapper;

    @Resource
    PileMapper pileMapper;

    @Resource
    StationMapper stationMapper;

    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result<?> save(@RequestBody Order order) {
        orderMapper.insert(order);
        return Result.success();
    }


    /*@PostMapping("/start")
    public Result<?> startCharge1(@RequestBody Order order) {

        Date date = new Date(); // this object contains the current date value

        order.setStartTime(date);
        order.setPileName(pileMapper.selectPileNameById(order.getPileId()));
        order.setStationId(pileMapper.selectStationIdById(order.getPileId()));
        order.setStationName(stationMapper.selectStationNameById(order.getStationId()));
        order.setState("充电中");
        pileMapper.updatePileStateStartById(order.getPileId());
        orderMapper.insert(order);
        return Result.success();
    }*/


    @PostMapping("/reserve")
    public Result<?> reserveCharge(@RequestBody Order order) {
        BigDecimal account=userMapper.selectById(order.getUserId()).getAccount();
        if(account.compareTo(order.getAmount())>-1){

            account=account.subtract(order.getAmount());
            userMapper.updateAccountById(order.getUserId(),account);

            Date reserveDate = new Date(); // this object contains the current date value
            order.setPayTime(reserveDate);

            order.setPileName(pileMapper.selectPileNameById(order.getPileId()));
            order.setStationId(pileMapper.selectStationIdById(order.getPileId()));
            order.setStationName(stationMapper.selectStationNameById(order.getStationId()));
            order.setState("已预约");
            pileMapper.updatePileStateReserveById(order.getPileId());
            orderMapper.insert(order);
            return Result.success(account);
        }else{
            return Result.error("1","账户余额不足");
        }
    }

    @DeleteMapping("/cancel")
    public Result<?> cancel(@RequestBody Order order) {
        //更新充电桩状态
        pileMapper.updatePileStateById(order.getPileId());
        //返回金额
        BigDecimal account=(userMapper.selectById(order.getUserId()).getAccount()).add(order.getAmount());
        userMapper.updateAccountById(order.getUserId(),account);
        //删除订单
        orderMapper.deleteById(order.getId());
        return Result.success(account);
    }

    @PutMapping
    public Result<?> update(@RequestBody Order order) {
        orderMapper.updateById(order);
        return Result.success();
    }

    @PutMapping("/end")
    public Result<?> updateState(@RequestBody Order order) {

        //更新订单状态
        orderMapper.updateOrderStateById(order.getId());

        //更新充电桩状态
        pileMapper.updatePileStateById(order.getPileId());

        Date date = new Date(); // this object contains the current date value
        //更新结束时间
        orderMapper.updateEndTimeById(order.getId(),date);

        return Result.success();
    }

    @PutMapping("/start")
    public Result<?> startCharge(@RequestBody Order order) {

        Date startDate = new Date(); // this object contains the current date value

        order.setStartTime(startDate);
        order.setState("充电中");
        orderMapper.updateById(order);
        pileMapper.updatePileStateStartById(order.getPileId());

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        orderMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(orderMapper.selectById(id));
    }

    @GetMapping("/totalAmount")
    public Result<?> totalAmount() {
        return Result.success(orderMapper.totalAmount());
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(orderMapper.selectList(null));
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "-1") Integer userId) {

        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
        if (userId!=-1) {
            wrapper.eq(Order::getUserId, userId);
        }

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Order::getUsername, search);
        }
        Page<Order> OrderPage = orderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(OrderPage);
    }
}
