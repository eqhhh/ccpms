package com.example.springbootccpms.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExportCsvMapper {

    //单个表导出，小数据查询使用
    List<Map> SelAll(String table);
    List<Map> Sel(int prev,int amount,String table);
    int SelIdCount(String table);
    String[] SelKey(String table);

    //开放sql查询语句使用
    int SelIdCountCommon(String sql);
    List<Map> SelCommon(int amount, String sql);
}
