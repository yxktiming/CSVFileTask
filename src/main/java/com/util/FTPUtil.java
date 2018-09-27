package com.util;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class FTPUtil {

    private static FTPClient ftpClient;
    private static Properties p = new Properties();

    /**
     * 获取ftp连接
     */
    private static void connectFtp() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ftp.properties");
        try {
            p.load(inputStream);
            ftpClient = new FTPClient();
            ftpClient.connect(p.getProperty("ftp.address"), 21);
            ftpClient.login(p.getProperty("ftp.username"), p.getProperty("ftp.password"));
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ftp上传文件
     *
     * @param file 上传文件
     */
    private static void upload(File file) {

        try {
            if (file != null && file.isDirectory()) {
                //ftpClient.makeDirectory(file.getName());
                //ftpClient.changeWorkingDirectory(file.getName());
                String[] files = file.list();
                if (files != null) {
                    for (String fileName : files) {
                        File f = new File(file.getPath() + "/" + fileName);
                        if (f.isDirectory()) {
                            upload(f);
                            ftpClient.changeToParentDirectory();
                        } else if (f.getName().endsWith(".csv")) {
                            FileInputStream input = new FileInputStream(f);
                            ftpClient.storeFile(f.getName(), input);
                            input.close();
                        }
                    }
                }
            } else if (file != null && file.getName().endsWith(".csv")) {
                FileInputStream input = new FileInputStream(file);
                ftpClient.storeFile(file.getName(), input);
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * 关闭ftp连接
     */
    private static void closeFtp() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ftp上传文件
     *
     * @param file 上传路径
     */
    public static void uploadFile(File file) {

        connectFtp();
        upload(file);
        closeFtp();


    }

    public static void main(String[] args) {
        uploadFile(new File("C:/Users/yxk/Desktop/csv/1.csv"));
    }

}
