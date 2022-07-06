package com.example.springbootccpms.service.exportcsvService;

import com.example.springbootccpms.mapper.ExportCsvMapper;
import com.example.springbootccpms.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GivenSqlService {
    @Autowired
    private ExportCsvMapper exportCsvMapper;

    public static boolean columnNameStatus=true;
    //CSV文件分隔符
    private final static String NEW_LINE_SEPARATOR="\n";
    /** CSV文件列分隔符 */
    private static final String CSV_COLUMN_SEPARATOR = ",";
    /** CSV文件列分隔符 */
    private static final String CSV_RN = "\r\n";

    //自定义的返回数据
    public static class OutDataPlus{
        public byte[] byteDataList;
        public BigInteger maxId;
        public boolean complete;
        public OutDataPlus(byte[] byteDataList,BigInteger maxId,boolean complete){
            this.byteDataList=byteDataList;
            this.maxId=maxId;
            this.complete=complete;
        }
    }

    public OutDataPlus exportCsv(BigInteger maxId, int singleCapacity, String sql) {
        byte[] byteDataList=null;
        boolean complete=false;
        BigInteger max=null;
        try {
            //获取sql语句
            String[] sqlStr=sql.split(" ");
            int tableLocation=-1;
            for (int i = 0; i < sqlStr.length; i++) {
                if (sqlStr[i].equals("from")) {
                    tableLocation = i + 1;
                    break;
                }
            }
            //表头的数组定义
            String[] sTitles;
            String newSql;
            if(sqlStr[1].equals("*")) {
                //获取表头（列名）
                if (tableLocation != -1 && tableLocation <= sqlStr.length) {
                    String[] s = exportCsvMapper.SelKey(sqlStr[tableLocation]);//表头（列名）
                    sTitles=s[0].split(",");

                    //转换出新的sql语句
                    StringBuilder stringBuilder=new StringBuilder();
                    boolean whereExist=false;
                    for(int i=0;i<sqlStr.length;i++){
                        if(sqlStr[i].equals("where")){
                            sqlStr[i+1]="id > "+maxId+" and "+sqlStr[i+1];
                            whereExist=true;
                            break;
                        }
                    }
                    if(!whereExist){
                        sqlStr[3]=sqlStr[3]+" where id > "+maxId+" ";
                    }
                    //
                    for(int i=0;i<sqlStr.length;i++){
                        stringBuilder.append(sqlStr[i]).append(" ");
                    }
                    newSql=stringBuilder.toString();

                } else {
                    throw new Exception("sql语句错误");
                }
            }
            else{
                sqlStr[1]="id,"+sqlStr[1];
                sTitles=sqlStr[1].split(",");

                //转换出新的sql语句
                StringBuilder stringBuilder=new StringBuilder();
                boolean whereExist=false;
                for(int i=0;i<sqlStr.length;i++){
                    if(sqlStr[i].equals("where")){
                        sqlStr[i+1]="id > "+maxId+" and "+sqlStr[i+1];
                        whereExist=true;
                        break;
                    }
                }
                if(!whereExist){
                    sqlStr[3]=sqlStr[3]+" where id > "+maxId+" ";
                }
                //
                for(int i=0;i<sqlStr.length;i++){
                    stringBuilder.append(sqlStr[i]).append(" ");
                }
                newSql=stringBuilder.toString();

            }
            //添加5000条
            List<Map> dataList = new ArrayList<>(exportCsvMapper.SelCommon(singleCapacity, newSql));
            if(dataList.size()!=singleCapacity){
                complete=true;
            }
            System.out.println(dataList.get(0));
            //找出dataList最后一个id
            if(!complete) {
                max = new BigInteger(String.valueOf(dataList.get(dataList.size() - 1).get("id")));
            }
            System.out.println(max);
            byteDataList=(Objects.requireNonNull(CsvUtil.doExport(sTitles, dataList))).toByteArray();

        }catch (Exception e){
            e.printStackTrace();
        }
        return new OutDataPlus(byteDataList,max,complete);
    }

    //用来输出列名
    public List<byte[]> columnName(String sql) {
        List<byte[]> columnNameDataList=new ArrayList<>();
        try {
            //获取sql语句
            String[] sqlStr = sql.split(" ");
            int tableLocation = -1;
            for (int i = 0; i < sqlStr.length; i++) {
                if (sqlStr[i].equals("from")) {
                    tableLocation = i + 1;
                    break;
                }
            }
            //表头的数组定义
            String[] sTitles;
            if(sqlStr[1].equals("*")) {
                //获取表头（列名）
                if (tableLocation != -1 && tableLocation <= sqlStr.length) {
                    String[] s = exportCsvMapper.SelKey(sqlStr[tableLocation]);//表头（列名）
                    sTitles=s[0].split(",");
                } else {
                    throw new Exception("sql语句错误");
                }
            }
            else{
                sTitles=sqlStr[1].split(",");
            }
            if (columnNameStatus) {
                // 输出列头(确保只输出一次)
                StringBuilder buf = new StringBuilder();

                for (int i = 0; i < sTitles.length; i++) {
                    buf.append(sTitles[i]).append(CSV_COLUMN_SEPARATOR);
                }
                buf.append(CSV_RN);
                columnNameDataList.add(buf.toString().getBytes("GBK"));
                columnNameStatus = false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return columnNameDataList;
    }

    //用来确定要执行的sql语句
    public List<String> getSqlList(String sql,String startTime,String endTime){

        String[] sqlArray=sql.split(" ");//存放http输入要查询sql语句的List
        List<String> sqlList=new ArrayList<>();//存放真正要执行查询sql语句的List
        int location=-1;//给出一个不能为数组下标的数据来初始化location
        //TimeInt/100是年份，TimeInt%100是月份
        int endYear=Integer.parseInt(endTime)/100;
        int endMonth=Integer.parseInt(endTime)%100;
        int startYear=Integer.parseInt(startTime)/100;
        int startMonth=Integer.parseInt(startTime)%100;

        //对于数据的处理和执行程序一般都放在service中，下次注意
        try {
            for (int i = 0; i < sqlArray.length; i++) {
                if (sqlArray[i].equals("from")) {
                    location = i + 1;
                    break;
                }
            }
            //判断语句是否合理
            if(location!=-1 && location!=sqlArray.length) {
                if (endYear > startYear || (endYear == startYear && endMonth >= startMonth)) {
                    if (endMonth > 0 && endMonth < 13 && startMonth > 0 && startMonth < 13) {
                        StringBuilder sqlStr = new StringBuilder("");
                        int selMonths=(endMonth - startMonth) + (endYear - startYear) * 12 + 1;//总共要查询几个月
                        for (int i = 0; i < selMonths; i++) {
                            if (endMonth <= 0) {
                                endMonth = 12;
                                endYear--;
                            }
                            //这里使用clone()而不是=，因为"="是引用
                            // 因为使用等号"="会导致在修改temporarySqlArray[location]时也会修改到sqlArray[location]
                            sqlStr.delete(0,sqlStr.length());
                            String[] temporarySqlArray=sqlArray.clone();
                            //判断是否为一到九月，如果是的话要在月份前加"0"
                            if (endMonth < 10) {
                                temporarySqlArray[location] =temporarySqlArray[location]+"_"+endYear+"0"+endMonth;
                            } else {
                                temporarySqlArray[location] =temporarySqlArray[location]+"_"+endYear+endMonth;
                            }
                            //将所有要执行的sql语句放入sqlList中
                            for(int j=0;j<temporarySqlArray.length;j++){
                                sqlStr.append(temporarySqlArray[j]).append(" ");
                            }

                            sqlList.add(sqlStr.toString());
                            endMonth--;
                        }
                        System.out.println(sqlList);//测试输出sqlList
                    }
                }
            }
            else{
                throw new Exception("sql语句错误");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sqlList;
    }
}
