package com;

import com.service.CSVQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

@Component
public class timerTask {
    private static final Logger logger = LogManager.getLogger(TimerTask.class.getName());

    //设定查询时间节点
    //当前时间点
    private static long currentTimeMills = System.currentTimeMillis();
    //一个半小时前时间点
    private static long oneAndHalf = currentTimeMills - 60 * 60 * 1000;
    //半小时前时间点
    private static long half = currentTimeMills;
    //将long类型转化为日期格式yyyy-MM-dd HH:mm:ss
    private static String previousTime = longToDate(oneAndHalf);
    private static String latterTime = longToDate(half);


    public static void task() {
        logger.info("查询时间区间：" + previousTime + "," + latterTime);
        //设定要指定任务
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH0000");
        String date = dateFormat.format(now);
        //查询到的总行数
        int rowNum = CSVQuery.QueryRowNum(previousTime, latterTime);
        logger.info("查询条数" + rowNum);
        //每页行数
        int pageSize = 100000;
        //总页数
        int totalPage;
        if (rowNum % pageSize == 0) {
            totalPage = rowNum / pageSize;
        } else {
            totalPage = rowNum / pageSize + 1;
        }
        logger.info("文件数：" + totalPage);
        //当前页码
        int pageNum;

        for (pageNum = 1; pageNum <= totalPage; pageNum++) {
            //0代表前面补零，3代表输出3位
            String str = String.format("%03d", pageNum);
            //设置文件名（当前日期+序列号 序列号每小时从001开始）
            String fileName = date + str + ".csv";
            //设置文件路径
            String createPath = "C:/Users/yxk/Desktop/csv/" + fileName;
            //当前行数
            int currentRow = (pageNum - 1) * pageSize;
            //生成CSV文件
            File csvFile = CSVFileCreate.createCSVFile(previousTime, latterTime, currentRow, pageSize, createPath);
            if (csvFile != null) {
                logger.info("成功生成文件：" + csvFile.getName());
            }
            //上传CSV文件
            // FTPUtil.uploadFile(csvFile);
            //if(csvFile!=null) {
            //    logger.info("成功上传文件：" + csvFile.getName());
            //}
            //更新保单号
            // CSVUpdate.UpdatePolicyNo();
            //logger.info("保单号更新完成");

        }
    }

    /**
     * long类型转换成日期
     *
     * @param lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     */
    static String longToDate(long lo) {
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        return sd.format(date);
    }

    public static void main(String[] args) {
        task();
    }
}
