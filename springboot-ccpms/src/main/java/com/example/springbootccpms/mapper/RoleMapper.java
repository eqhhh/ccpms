package com.example.springbootccpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootccpms.entity.Role;
import com.example.springbootccpms.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from user_role where user_id = #{userId}")
    List<UserRole> getUserRoleByUserId(Integer userId);


    @Delete("delete from user_role where user_id = #{userId}")
    void deleteRoleByUserId(Integer userId);

    @Insert("insert into user_role(user_id, role_id) values(#{userId}, #{roleId})")
    void insertUserRole(Integer userId, Integer roleId);

    @Delete("delete from role_permission where permission_id = #{id}")
    void deleteRolePermissionByPermissionId(Integer id);
}

