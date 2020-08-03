package cn.ffcs.buffet.controller;

import cn.ffcs.buffet.common.dto.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 图片上传
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/8/3 21:29
 */
@Api(value = "/upload", tags = "图片上传")
@RestController
public class UploadDownController{
    private static final Logger logger = LoggerFactory.getLogger(UploadDownController.class);
    /**
     * 文件上传
     * @param picture 图片名称
     * @param request HttpServletRequest
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("picture") MultipartFile picture, HttpServletRequest request) throws IOException {

        //获取文件的储存位置
        String path = "d:/upload/";
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        logger.info("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        logger.info("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        logger.info("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);

        //将文件保存到服务器指定位置
        picture.transferTo(targetFile);
        logger.info("上传成功");
        //将文件在服务器的存储路径返回
        return Result.success("/upload/" + fileName);
    }
}
