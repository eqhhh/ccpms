package com.example.springbootccpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootccpms.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("delete from user_role where user_id=#{userId}")
    void deleteByUserRoleId(Long userId);
}

