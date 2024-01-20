package com.yexingyi.controller;

import com.yexingyi.entity.TranslatedImg;
import com.yexingyi.model.ResponseMsg;
import com.yexingyi.service.FileService;
import com.yexingyi.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author YeXingyi
 * @version 1.0
 * @date 2024/1/20 22:06
 */
@Api(tags = "上传文件相关接口")
@Slf4j
@RestController
@RequestMapping("/pet/upload")
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传翻译的图片")
    @PostMapping("/TranslatedImage")
    public ResponseMsg uploadTranslatedImage(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()){
        return ResponseMsg.error("文件为空");
        }
        //如果文件大小超过10M
        if (file.getSize() > 10485760){
            return ResponseMsg.error("文件大小超过10M");
        }
        String contentType = file.getContentType();
        if (!contentType.startsWith("image/")){
            return ResponseMsg.error("文件格式不正确");
        }

        try {
            // 文件保存的目录
            String directory = "E:\\ProjectCompetition\\pet\\file\\translatedImg\\";
            Path directoryPath = Paths.get(directory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // 生成随机唯一文件名，保持原文件的扩展名
            String extension = contentType.substring(contentType.indexOf("/") + 1);
            String filename = UUID.randomUUID().toString() + "." + extension;

            // 构建保存的文件的完整路径
            Path filePath = directoryPath.resolve(filename);

            // 保存文件
            file.transferTo(filePath.toFile());

            TranslatedImg translatedImg = new TranslatedImg();
            translatedImg.setUrl(directory);
//            translatedImg.setUserId(UserUtils.getCurrentUser().getId());
            translatedImg.setUserId(1L);

            fileService.insertTranslatedImg(translatedImg);
            // 返回成功响应（可能需要调整以符合你的响应格式）
            return ResponseMsg.ok("图片文件上传成功", translatedImg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMsg.error("文件上传失败");
        }
    }
}
