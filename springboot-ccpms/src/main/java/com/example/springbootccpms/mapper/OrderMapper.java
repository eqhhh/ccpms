package com.example.springbootccpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootccpms.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Update("update charging_order set state = '已完成' where id=#{id}")
    void updateOrderStateById(Integer id);

    @Update("update charging_order set end_time = #{date} where id=#{id}")
    void updateEndTimeById(Integer id, Date date);

    @Select("select sum(amount) from charging_order")
    BigDecimal totalAmount();
}
