package com.lin.controller;


import com.lin.common.api.ApiResult;
import com.lin.common.exception.ApiAsserts;
import com.lin.model.entity.UmsUser;
import com.lin.service.IBmsPostService;
import com.lin.service.IUmsUserService;
import com.lin.service.impl.UploadService;
import com.lin.utils.FileUtil;
import com.lin.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lin.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private IBmsPostService iBmsPostService;

    @Resource
    private IUmsUserService umsUserService;

    @Autowired
    UploadService uploadService;

    @Value("${img.imgURL}")
    private String imgURL;

    @Value("${upload.dir}")
    private String uploadDir;

    //修改用户头像
    @PostMapping(value="/avatar")
    public ApiResult<Object> update(@RequestHeader(value = USER_NAME) String userName,@RequestParam("file") MultipartFile file) throws IOException {
        if(file==null) ApiAsserts.fail("文件为空，请重新上传！");
        UmsUser user = umsUserService.getUserByUsername(userName);
        //如果用户已经存在,更新
        if(user!=null){
            //处理文件名
            int lastIndexOf = file.getOriginalFilename().lastIndexOf(".");
            //获取文件的后缀名
            String suffix = file.getOriginalFilename().substring(lastIndexOf+1);
            // fileTypeList
            List fileTypeList = new ArrayList();
            fileTypeList.add("jpg");
            fileTypeList.add("png");
            fileTypeList.add("jpeg");
            // 获取缓存路径
            String cachePath = FileUtil.getFileAbsolutePath(uploadDir+".cache/",suffix);
            // press
            MultipartFile compressFile = ImageUtil.compressFile(file, cachePath, fileTypeList);
            //上传
            String fileName = FileUtil.getFileAbsolutePath("",suffix);
            String filePath = uploadDir+fileName;
            String res = uploadService.upload(compressFile,filePath);
            if(res=="error"){
                ApiAsserts.fail("上传失败！");
            }
            // 删除原有头像
            String oldAvatar = user.getAvatar();
            oldAvatar = oldAvatar.substring(imgURL.length());
            File oldFile = new File(uploadDir+oldAvatar);
            oldFile.delete();
            //保存
            user.setAvatar(imgURL+fileName);
            umsUserService.updateById(user);
            return ApiResult.success("sucess");
        }
        else
            ApiAsserts.fail("用户不存在！");
        return null;
    }

    //上传图片
    @PostMapping(value="/upload")
    public ApiResult<Object> updateImg(@RequestHeader(value = USER_NAME) String userName,@RequestParam("file") MultipartFile file) throws IOException {
        UmsUser user = umsUserService.getUserByUsername(userName);
        //如果用户已经存在,更新
        if(user!=null){
            //处理文件名
            int lastIndexOf = file.getOriginalFilename().lastIndexOf(".");
            //获取文件的后缀名
            String suffix = file.getOriginalFilename().substring(lastIndexOf+1);
            // fileTypeList
            List fileTypeList = new ArrayList();
            fileTypeList.add("jpg");
            fileTypeList.add("png");
            fileTypeList.add("jpeg");
            // 获取缓存路径
            String cachePath = FileUtil.getFileAbsolutePath(uploadDir+".cache/",suffix);
            // press
            MultipartFile compressFile = ImageUtil.compressFile(file, cachePath, fileTypeList);
            //上传
            String fileName = FileUtil.getFileAbsolutePath("",suffix);
            String filePath = uploadDir+fileName;
            String res = uploadService.upload(compressFile,filePath);
            if(res=="error"){
                ApiAsserts.fail("上传失败！");
            }
            Map<String, Object> map = new HashMap<>(16);
            //返回markdown
            String path="!["+fileName+"]("+imgURL+fileName+")";
            map.put("path",path);
            return ApiResult.success(map);
        }
        else
            ApiAsserts.fail("用户不存在！");
        return null;
    }

}
