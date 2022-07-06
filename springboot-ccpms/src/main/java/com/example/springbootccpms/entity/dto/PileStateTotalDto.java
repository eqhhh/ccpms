package com.example.springbootccpms.entity.dto;

import lombok.Data;

@Data
public class PileStateTotalDto {
    private Integer totalPile;
    private Integer totalIdlePile;
    private Integer totalReservedPile;
    private Integer totalChargingPile;
    private Integer totalRepairPile;
}
