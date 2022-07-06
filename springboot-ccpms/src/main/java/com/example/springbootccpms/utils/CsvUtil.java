package com.example.springbootccpms.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导出至csv文件
 * */
public class CsvUtil {
    //CSV文件分隔符
    private final static String NEW_LINE_SEPARATOR="\n";
    /** CSV文件列分隔符 */
    private static final String CSV_COLUMN_SEPARATOR = ",";
    /** CSV文件列分隔符 */
    private static final String CSV_RN = "\r\n";

    /**
     * @param colNames 表头部数据
     * @param dataList 集合数据
     */
    //version2.0
    public static ByteArrayOutputStream doExport(String[] colNames, List<Map> dataList) {
        try {
            //需要线程安全时使用StringBuffer
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            //OutputStream os = new ByteArrayOutputStream();

            // 写出响应
            if (null != dataList) { // 输出数据
                int dataListSize=dataList.size();
                StringBuilder bud=new StringBuilder();
                for (int i = 0; i < dataListSize; i++) {
                    //转换一组数据之后将其删除释放内存
                    bud.delete(0,bud.length());
                    for (int j = 0; j < colNames.length; j++) {
                        bud.append(dataList.get(0).get(colNames[j])).append(CSV_COLUMN_SEPARATOR);
                    }
                    bud.append(CSV_RN);
                    dataList.remove(0);
                    //输出到缓冲区
                    os.write(bud.toString().getBytes("GBK"));
                    os.flush();
                    //System.out.println("log2:"+i);//输出测试2
                }
                System.out.println("log2:datalist successfully exported CSV file!");//输出测试2
            }
            os.flush();
            os.close();//关闭流
            return os;
        } catch (Exception e) {
            //LogUtils.error("doExport错误...", e);
            e.printStackTrace();
        }
        return null;
    }

    //设置响应头
    public static HttpHeaders setCsvHeader(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        try {
            // 设置文件后缀
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = new String(fileName.getBytes("gbk"), "iso8859-1") + sdf.format(new Date()) + ".csv";
            headers.add("Pragma", "public");
            headers.add("Cache-Control", "max-age=30");
            headers.add("Content-Disposition", "attachment;filename="+fileName);
            headers.setContentType(MediaType.valueOf("application/vnd.ms-excel;charset=UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return headers;
    }
}
