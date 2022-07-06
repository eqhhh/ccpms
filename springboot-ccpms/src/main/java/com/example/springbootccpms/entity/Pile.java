package com.example.springbootccpms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@TableName("pile")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pile {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String pileName;
    private Integer stationId;
    private String state;
    private String remark;
}
