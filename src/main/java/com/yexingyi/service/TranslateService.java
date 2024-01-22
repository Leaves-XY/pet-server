package com.yexingyi.service;

import com.yexingyi.client.PredictClient;
import com.yexingyi.entity.PredictionResult;
import com.yexingyi.entity.ResnetResult;
import com.yexingyi.entity.TranslatedImg;
import com.yexingyi.model.ResponseMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * @author YeXingyi
 * @version 1.0
 * @date 2024/1/22 21:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TranslateService {
    PredictClient predictClient = new PredictClient();

    public ResponseMsg predict(TranslatedImg translatedImg) {
        String url = translatedImg.getUrl();
        File file =new File(url);

        PredictionResult predictionResult =predictClient.uploadFile(file);

        if (predictionResult==null){
            return ResponseMsg.error("解析失败,请联系管理员");
        }
        for (Map.Entry<String, ResnetResult> entry : predictionResult.getResnetResult().entrySet()) {
            ResnetResult resnetResult = entry.getValue();
            String base64Image = resnetResult.getImg();
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            try {
                // 将字节转换为 BufferedImage
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                String filename = new File(url).getName(); // 从路径中仅提取文件名
                filename = "image_result_" + filename.substring(0, filename.lastIndexOf("."))+"_"+entry.getKey() + ".png";
                String osName = System.getProperty("os.name").toLowerCase();
                String directory;
                if (osName.contains("win")) {
                    // Windows 操作系统
                    directory = "E:\\ProjectCompetition\\pet\\file\\translatedImg\\result";
                } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
                    // Linux 或 Unix 或 Mac 操作系统
                    directory = "/www/pet/file/translatedImg/result";
                } else {
                    // 其他操作系统
                    return ResponseMsg.error("未知操作系统，无法确定文件保存路径");
                }

                String filePath = directory + File.separator +filename;

                // 将图片保存为 PNG 格式
                File outputFile = new File(filePath);
                //如果路径不存在，创建路径
                if (!outputFile.getParentFile().exists()) {
                    outputFile.getParentFile().mkdirs();
                }
                ImageIO.write(image, "png", outputFile);

                // 更新 img 属性为文件路径
                resnetResult.setImg(filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseMsg.ok("翻译成功",predictionResult);
    }
}
