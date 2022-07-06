package com.example.springbootccpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootccpms.entity.Pile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PileMapper extends BaseMapper<Pile> {

    @Update("update pile set station_name = #{stationId} where user_id=#{id}")
    void changeStationById(Integer id,Integer stationId);

    @Update("update pile set state = '空闲' where id=#{id}")
    void updatePileStateById(Integer id);

    @Update("update pile set state = '维修中' where id=#{id}")
    void updateAbnormalPileById(Integer id);

    @Select("select * from pile where state= '空闲' ")
    List<Pile> selectIdlePileList();

    @Select("select pile_name from pile where id=#{id} ")
    String selectPileNameById(Integer id);

    @Select("select station_id from pile where id=#{id} ")
    Integer selectStationIdById(Integer id);

    @Update("update pile set state = '充电中' where id=#{id}")
    void updatePileStateStartById(Integer id);

    @Update("update pile set state = '已预约' where id=#{id}")
    void updatePileStateReserveById(Integer id);

    @Select("select count(id) from pile where state= '空闲' ")
    Integer selectIdlePileQuantity();

    @Update("update pile set state = '空闲' where station_id=#{id}")
    void updatePileStateByStationId1(Integer id);

    @Update("update pile set state = '维修中' where station_id=#{id}")
    void updatePileStateByStationId2(Integer id);

    @Select("select count(id) from pile where state= '已预约' ")
    Integer selectTotalReservedPileQuantity();
    @Select("select count(id) from pile ")
    Integer selectTotalPileQuantity();
    @Select("select count(id) from pile where state= '充电中' ")
    Integer selectTotalChargingPileQuantity();
    @Select("select count(id) from pile where state= '维修中' ")
    Integer selectTotalRepairPileQuantity();
}
