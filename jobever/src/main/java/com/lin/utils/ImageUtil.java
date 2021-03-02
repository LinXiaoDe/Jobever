package com.lin.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 获取图片尺寸和截图 工具类
 *
 * @author zhanglifeng
 * @date 2019-11-08
 */
public class ImageUtil {
    private final static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * [压缩要上传的文件，并将文件类型转为指定的文件类型集合中的一个。]
     *
     * @param file         要上传的文件
     * @param absolutePath 临时文件的绝对路径
     * @param fileTypeList 文件转成的类型集合
     * @return
     */
    public static MultipartFile compressFile(MultipartFile file, String absolutePath, List<String> fileTypeList) throws IOException {
        //记录原MultipartFile，如果压缩异常就用原来的MultipartFile
        MultipartFile oldMultipartFile = file;
        FileInputStream fileInputStream = null;
        try {
            String fileName = file.getName();
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            //获取要上传的文件后缀
            String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            File tempFile = new File(absolutePath);
            if (fileTypeList.contains(fileExt)) {
                //如果文件类型符合，则只压缩就好了
                Thumbnails.of(file.getInputStream())
                        .scale(0.2f)
                        .outputQuality(0.2f)
                        .toFile(tempFile);
            } else {
                //如果文件类型不在指定的集合内。则要转格式，并压缩
                Thumbnails.of(file.getInputStream())
                        .scale(0.2f)
                        .outputFormat(fileTypeList.get(0))
                        .outputQuality(0.2f)
                        .toFile(tempFile);
                originalFilename = originalFilename.replace("." + fileExt, "." + fileTypeList.get(0));
            }
            fileInputStream = new FileInputStream(tempFile);
            file = new MockMultipartFile(fileName, originalFilename, contentType, fileInputStream);
             boolean success = tempFile.delete();
             logger.info("删除临时file success：{}", success);
        } catch (IOException e) {
            logger.error("压缩图片失败,把MultipartFile赋值为原来的值oldFile,exception：{}", e);
            file = oldMultipartFile;
        } finally {
            fileInputStream.close();
        }
        return file;
    }

}
