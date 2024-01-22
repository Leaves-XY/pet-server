package com.yexingyi.service;

import com.yexingyi.client.PredictClient;
import com.yexingyi.dao.TranslatedImgDao;
import com.yexingyi.entity.TranslatedImg;
import com.yexingyi.model.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author YeXingyi
 * @version 1.0
 * @date 2024/1/20 22:29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileService {
    @Autowired
    private TranslatedImgDao translatedImgDao;
    @Autowired
    private TranslateService translateService;

    public ResponseMsg uploadTranslatedImage(MultipartFile file) throws IOException {
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
            String osName = System.getProperty("os.name").toLowerCase();

            String directory;
            if (osName.contains("win")) {
                // Windows 操作系统
                directory = "E:\\ProjectCompetition\\pet\\file\\translatedImg\\";
            } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
                // Linux 或 Unix 或 Mac 操作系统
                directory = "/www/pet/file/translatedImg/";
            } else {
                // 其他操作系统
                return ResponseMsg.error("未知操作系统，无法确定文件保存路径");
            }

            // 文件保存的目录
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
            translatedImg.setUrl(directory + filename);
            // translatedImg.setUserId(UserUtils.getCurrentUser().getId());
            translatedImg.setUserId(1L);

            translatedImgDao.insert(translatedImg);
            // 返回成功响应
            return ResponseMsg.ok("图片文件上传成功", translatedImg);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseMsg.error("文件上传失败");
        }
    }
}
