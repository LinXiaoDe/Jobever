package com.lin.utils;

import java.io.File;
import java.util.UUID;

/**
 * 文件操作工具类
 * @date 2019-11-09
 * @author zhanglifeng
 */
public class FileUtil {
    public static String getFileAbsolutePath(String filePath,String fileType){
        StringBuilder sb =new StringBuilder();
        String fileName = sb.append(UUID.randomUUID()).append(".").append(fileType).toString();
        sb.setLength(0);
        sb.append(filePath).append(fileName);
        String path = sb.toString().replaceAll("\\\\", "\\/");
        return path;
    }
}
