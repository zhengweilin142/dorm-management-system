package com.it.dorm.controller;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {


    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";
    // 项目启动的ip地址
    // 给 ip 一个默认值，防止忘定义时报错
    @Value("${ip:localhost}")
    String ip;
    // 项目启动的端口号
    @Value("${server.port}")
    String port;

    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 文件的原始名称    aaa.png
        log.info("文件的原始名称：{}", originalFilename);
        String mainName = FileUtil.mainName(originalFilename);  // 文件的主名称    aaa
        log.info("文件的原始主名称：{}", mainName);
        String extName = FileUtil.extName(originalFilename);  // 文件的扩展名(后缀)    .png
        log.info("文件的原始后缀：{}", extName);
        System.out.println();

        // 如果当前文件的父级目录不存在，就创建
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);    // 如果当前文件的父级目录不存在，就创建
        }

        // 如果当前上传的文件已经存在了，那么这个时候我就要重命名一个文件
        if (FileUtil.exist(ROOT_PATH + File.separator + originalFilename)) {
            originalFilename = System.currentTimeMillis() + "-" + mainName + "." + extName;
            log.info("文件已经存在，重命名后的文件名：{}", originalFilename);
        }

        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);   // 要保存的文件地址/目录
        // 存储文件到本地的磁盘里面去
        file.transferTo(saveFile);
        // 返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的
        String url = "http://" + ip + ":" + port + "/file/download?fileName=" + originalFilename;
        log.info("文件的下载地址：{}", url);
        return url;
    }

    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
        String filePath = ROOT_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }


}
