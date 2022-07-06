package com.example.springbootccpms.controller;

import com.example.springbootccpms.service.exportcsvService.*;
import com.example.springbootccpms.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ExportCsvController {
    @Autowired
    private SmallTableExportCsvService smallTableExportCsvService;
    @Autowired
    private BigTableService bigTableService;
    @Autowired
    private GivenSqlService givenSqlService;
    private final static String NEW_LINE_SEPARATOR="\n";

    //查询(数据大时使用，采用分段查询输出到文件)、获取指定的列______优化后版本
    @RequestMapping(value = "/givenSqlSelect")
    public void optimizedGivenSqlSelect(HttpServletResponse res, HttpServletRequest req, @RequestParam(value = "sql")String sql, @RequestParam(value = "startTime")String startTime, @RequestParam(value = "endTime")String endTime) throws IOException {
        //设置excel文件名和文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = new String("自定义查询信息表".getBytes("gbk"), "iso8859-1") + sdf.format(new Date()) + ".csv";

        //设置响应头
        res.addHeader("Pragma", "public");
        res.addHeader("Cache-Control", "max-age=30");
        res.setHeader("Content-disposition", "attachment; filename=" + fileName);
        res.setContentType("application/vnd.ms-excel;charset=UTF-8");

        //获取具体要执行的查询语句
        List<String> sqlList= givenSqlService.getSqlList(sql,startTime,endTime);
        int sqlListSize=sqlList.size();
        try {
            //根据路径创建文件，并设置编码格式
            ServletOutputStream out = res.getOutputStream();
            //OutputStreamWriter osw = new OutputStreamWriter(out, "GBK");
            //先输出列名，创建文件
            List<byte[]> outListColumnName=this.givenSqlService.columnName(sqlList.get(0));
            if (null != outListColumnName) {
                //循环写入数据
                for (byte[] lineData : outListColumnName) {
                    out.write(lineData);
                }
            }
            out.flush();
            System.out.println("文件存储路径准备就绪");
            for (int i = sqlListSize-1; i >=0; i--) {

                int singleCapacity = 5000;
                BigInteger maxId=new BigInteger("0");
                //执行循环写入
                for (int j = 0; j < 1111; j++) {
                    GivenSqlService.OutDataPlus outData=this.givenSqlService.exportCsv(maxId,singleCapacity,sqlList.get(i));
                    maxId=outData.maxId;
                    out.write(outData.byteDataList);
                    if(outData.complete){
                        break;
                    }
                    System.out.println(j);
                }
            }
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("ok");
    }

    //数据量较小的表全部导出到csv文件（60w行或者大约250MB）
    //表头未导出bug还未修改
    @RequestMapping(value = "/smallTableExportCsv")
    public ResponseEntity<byte[]> smallTableExportCsv(@RequestParam(value ="table")String table){
        //设置excel文件名
        String fileName="信息表";
        //设置HttpHeaders，设置fileName编码，排除导出文档名称乱码问题
        HttpHeaders headers = CsvUtil.setCsvHeader(fileName);
        byte[] value = null;
        try {
            //获取要导出的数据
            value = this.smallTableExportCsvService.exportCsv(table);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(value, headers, HttpStatus.OK);
    }

    //数据量大的表全部导出到csv文件______优化版本
    @RequestMapping(value = "/bigTableExportCsv")
    public void optimizedBigTableExportCsv(HttpServletResponse res, HttpServletRequest req,@RequestParam(value ="table")String table) throws UnsupportedEncodingException {
        //设置excel文件名和文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = new String("信息表".getBytes("gbk"), "iso8859-1") + sdf.format(new Date()) + ".csv";

        //设置响应头
        res.addHeader("Pragma", "public");
        res.addHeader("Cache-Control", "max-age=30");
        res.setHeader("Content-disposition", "attachment; filename=" + fileName);
        res.setContentType("application/vnd.ms-excel;charset=UTF-8");

        //获取具体要执行的查询语句
        String sql= "select * from " + table;
        try {
            //根据路径创建文件，并设置编码格式
            ServletOutputStream out = res.getOutputStream();
            //OutputStreamWriter osw = new OutputStreamWriter(out, "GBK");
            //先输出列名，创建文件
            List<byte[]> outListColumnName=this.bigTableService.columnName(sql);
            if (null != outListColumnName) {
                //循环写入数据
                for (byte[] lineData : outListColumnName) {
                    out.write(lineData);
                }
            }
            out.flush();
            System.out.println("文件存储路径准备就绪");

            int singleCapacity = 5000;
            BigInteger maxId=new BigInteger("0");
            //执行循环写入
            for (int j = 0; j < 3000; j++) {
                GivenSqlService.OutDataPlus outData=this.givenSqlService.exportCsv(maxId,singleCapacity,sql);
                maxId=outData.maxId;
                out.write(outData.byteDataList);
                if(outData.complete){
                    break;
                }
                System.out.println(j);
            }
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("ok");
    }
}
