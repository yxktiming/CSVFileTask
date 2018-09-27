package com;

import com.service.CSVQuery;
import com.util.CSVUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVFileCreate {
    /**
     * 生成csv文件
     *
     * @return csvFile
     */
    public static File createCSVFile(String previousTime, String latterTime, int currentRow, int pageSize, String path) {

        //设置列名
        LinkedHashMap<String, Object> columnName = new LinkedHashMap<>();
        columnName.put("1", "数据序号");
        columnName.put("2", "保单号");
        columnName.put("3", "套餐编码");
        columnName.put("4", "签单日期");
        columnName.put("5", "生效日期");
        columnName.put("6", "收费日期");
        columnName.put("7", "投保人姓名");
        columnName.put("8", "投保人性别");
        columnName.put("9", "投保人出生日期");
        columnName.put("10", "投保人证件类型");
        columnName.put("11", "投保人证件号码");
        columnName.put("12", "投保人手机号");
        columnName.put("13", "投保人邮箱");
        columnName.put("14", "投保人与被保人关系");
        columnName.put("15", "被保人姓名");
        columnName.put("16", "被保人性别");
        columnName.put("17", "被保人出生日期");
        columnName.put("18", "被保人证件类型");
        columnName.put("19", "被保人证件号码");
        columnName.put("20", "所在学校");

        List<Map<String, Object>> exportData = CSVQuery.QueryAll(previousTime, latterTime, currentRow, pageSize);

        //将数据写入文件

        File csvFile=null;
        if (!exportData.isEmpty()) {
             csvFile = CSVUtil.createCSVFile(exportData, columnName, path);
        }
        return csvFile;


    }

}

