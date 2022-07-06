package com.example.springbootccpms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootccpms.entity.User;
import com.example.springbootccpms.entity.dto.UserAddressDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 一对多查询
    Page<User> findPage(Page<User> page, @Param("nickName") String nickName);

    @Select("select count(id) count, address from user GROUP BY address")
    List<UserAddressDto> countAddress();

    // 查询用户名
    @Select("select password from user where username=#{username}")
    User selectByName(String username);

    @Update("update user set password = #{newPass} where id = #{userId}")
    int updatePass(Map<String, Object> map);

    @Update("update user set account = #{newAccount} where id = #{userId}")
    void rechargeById(Integer userId, BigDecimal newAccount);

    @Update("update user set account = #{newAccount} where id = #{userId}")
    void updateAccountById(Integer userId, BigDecimal newAccount);

}
