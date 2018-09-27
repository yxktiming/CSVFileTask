package com.util;

import org.apache.commons.beanutils.BeanUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVUtil {
    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param map        csv文件的列表头map
     * @param path       文件路径
     * @return csvFile
     */
    public static File createCSVFile(List exportData, LinkedHashMap map, String path) {
        File csvFile = null;
        try {
            csvFile = new File(path);

            // UTF-8使正确读取分隔符","
            //如果生产文件乱码，windows下用gbk，linux用UTF-8
            BufferedWriter csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GBK"), 1024);
            // 写入文件头部
            for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext(); ) {
                Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                csvFileOutputStream.write(propertyEntry.getValue() != null ? (String) propertyEntry.getValue() : "");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext(); ) {
                Object row = iterator.next();
                for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext(); ) {
                    Map.Entry propertyEntry = (Map.Entry) propertyIterator.next();
                    csvFileOutputStream.write(BeanUtils.getProperty(row, (String) propertyEntry.getKey()));
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
            csvFileOutputStream.close();
            System.out.println("成功创建文件："+csvFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

            return csvFile;
    }

}

