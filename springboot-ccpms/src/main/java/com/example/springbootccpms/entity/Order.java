package com.example.springbootccpms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@TableName("charging_order")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private Integer pileId;
    private String pileName;
    private Integer stationId;
    private String stationName;

    private String payChannel;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    private BigDecimal amount;
    private String state;
    private String remark;
}
