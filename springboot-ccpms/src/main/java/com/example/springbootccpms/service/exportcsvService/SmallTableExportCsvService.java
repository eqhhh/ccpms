package com.example.springbootccpms.service.exportcsvService;

import com.example.springbootccpms.mapper.ExportCsvMapper;
import com.example.springbootccpms.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SmallTableExportCsvService {     //数据量较小的数据表导出，测试过的大约60w行250MB文件
    @Autowired
    private ExportCsvMapper exportCsvMapper;

    public byte[] exportCsv(String table) {
        byte[] content = null;
        try {
            String[] s= exportCsvMapper.SelKey(table);//表头（列名）;
            String[] sTitles = s[0].split(",");
            int idCount = exportCsvMapper.SelIdCount(table);
            if(idCount < 100000){
                List<Map> dataList = exportCsvMapper.SelAll(table);
                ByteArrayOutputStream os = CsvUtil.doExport(sTitles,dataList);
                content = os.toByteArray();
            }
            else {
                int prevVariable=0;
                int singleCapacity=1000;
                List<Map> dataList = new ArrayList<>();
                for(int i=0;i <idCount/singleCapacity;i++){
                    dataList.addAll(exportCsvMapper.Sel(prevVariable,singleCapacity,table));
                     /*
                     //找出dataList最后一个id(输出的obj为null，暂时没有验证不知道原因)
                     //是因为在sql语句的处理上导致最后查询返回的结果中没有id这一项。OptimizedGivenSqlService已经解决。
                    Object obj = (dataList.get(dataList.size() - 1).get("id"));
                    if (obj instanceof Integer) {
                         prevVariable = Integer.parseInt(obj.toString());
                    }
                    */
                    prevVariable=prevVariable+singleCapacity;
                    System.out.println("log1:"+i);//输出测试
                }

                dataList.addAll(exportCsvMapper.Sel(prevVariable,idCount%singleCapacity,table));
                ByteArrayOutputStream os = CsvUtil.doExport(sTitles,dataList);
                assert os != null;//判断是否为空
                content = os.toByteArray();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }
}
