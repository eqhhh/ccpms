package com.example.springbootccpms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@TableName("user")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickName;

    private BigDecimal limitMoney;
    private String sex;
    private Integer age;
    private String address;
    private String phone;
    private String email;
    private String state;
    private BigDecimal account;
    private String remark;

    private String avatar;

    @TableField(exist = false)
    private List<Integer> roles;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private Set<Permission> permissions;
}
