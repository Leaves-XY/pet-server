package com.yexingyi.client;
import com.yexingyi.entity.PredictionResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
/**
 * @author YeXingyi
 * @version 1.0
 * @date 2024/1/22 20:06
 */
public class PredictClient {
    static String clientUrl="http://localhost:8000/predict";

    public PredictionResult uploadFile(File file) {
        RestTemplate restTemplate = new RestTemplate();

        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建文件资源
        FileSystemResource fileResource = new FileSystemResource(file);

        // 添加文件资源和其他表单数据到 MultiValueMap
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);

        // 创建请求实体
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送 POST 请求
        PredictionResult predictionResult = restTemplate.postForObject(clientUrl, requestEntity, PredictionResult.class);


        return predictionResult;
    }

    public static void main(String[] args) {
        PredictClient client = new PredictClient();
        // 替换为您的文件路径和目标 URL
        client.uploadFile( new File("C:\\Users\\Leaves_XY\\Desktop\\QQ截图20240121182007.jpg"));
    }
}


