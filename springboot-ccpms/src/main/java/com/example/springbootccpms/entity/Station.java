package com.example.springbootccpms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@TableName("station")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Station {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String stationName;
    private String state;
    private String remark;

    @TableField(exist = false)
    private List<Integer> piles;
}
