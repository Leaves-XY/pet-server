package com.yexingyi.controller;

import com.yexingyi.entity.TranslatedImg;
import com.yexingyi.model.ResponseMsg;
import com.yexingyi.service.FileService;
import com.yexingyi.service.TranslateService;
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

import java.io.IOException;
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
    @Autowired
    private TranslateService translateService;

    @ApiOperation(value = "上传翻译的图片")
    @PostMapping("/TranslatedImage")
    public ResponseMsg uploadTranslatedImage(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.uploadTranslatedImage(file);
    }

    @ApiOperation(value = "上传翻译图片并翻译")
    @PostMapping("/TranslatedImageAndPredict")
    public ResponseMsg uploadTranslatedImageAndPredict(@RequestParam("file") MultipartFile file) throws IOException {
        TranslatedImg translatedImg = (TranslatedImg) (fileService.uploadTranslatedImage(file)).getObj();
        return translateService.predict(translatedImg);
    }
}
