package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/imgs")
public class ImgController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    //private static final String UPLOAD_PATH = "e:\\imgs";

    @RequestMapping("/uploader")
    @ResponseBody
    public String uploadImg(MultipartFile file) {

        //获得上传文件后辍的下标 xxx.png
        int index = file.getOriginalFilename().lastIndexOf(".");//返回指定字符在此字符串中最后一次出现处的索引
        String suffix =file.getOriginalFilename().substring(index+1);
        System.out.println("截取到的文件名后辍： "+suffix);
        //文件上到FastDFS
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), suffix, null);
            //获取上传到FastDFS中图片的路径
            String imgUrl = storePath.getFullPath();
            System.out.println("FastDFS中图片的路径: "+imgUrl);

            //将存FastDFS中图片的路径以JSON方式返回到前端页面
            Map<String,Object> map = new HashMap<>();
            map.put("imgUrl",imgUrl);
            String param = JSON.toJSONString(map);
            return param;
            //return "{\"uploadPath\":\"" + imgUrl + "\"}";

        } catch (IOException e) {
            e.printStackTrace();
        }

        /* //文件上传到本地磁盘
        try (
                InputStream inputStream = file.getInputStream();
                OutputStream outputStream = new FileOutputStream(UPLOAD_PATH + UUID.randomUUID().toString());
        ) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return null;
    }
}
