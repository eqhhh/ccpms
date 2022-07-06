package com.example.springbootccpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootccpms.entity.Station;
import com.example.springbootccpms.entity.dto.IdlePileDto;
import com.example.springbootccpms.entity.dto.UserAddressDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StationMapper extends BaseMapper<Station> {

    @Select("select station_name from station where id=#{id} ")
    String selectStationNameById(Integer id);

    @Select("select count(id) count, station_id from pile where state = '空闲' GROUP BY station_id")
    List<IdlePileDto> countIdlePile();

    @Delete("delete from pile where station_id = #{id}")
    void deletePileByStationId(Integer id);

}
