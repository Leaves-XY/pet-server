package com.yexingyi.controller;

import com.yexingyi.client.PredictClient;
import com.yexingyi.entity.PredictionResult;
import com.yexingyi.entity.ResnetResult;
import com.yexingyi.entity.TranslatedImg;
import com.yexingyi.model.ResponseMsg;
import com.yexingyi.service.TranslateService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author YeXingyi
 * @version 1.0
 * @date 2024/1/22 20:53
 */
@Slf4j
@RestController
@Api(tags = "翻译接口")
@RequestMapping("/pet/translate")
public class TranslateController {
   @Autowired
    private TranslateService translateService;

    @PostMapping("/predict")
    public ResponseMsg predict(TranslatedImg translatedImg){
       return translateService.predict(translatedImg);
    }
}
