package com.yexingyi.service;

import com.yexingyi.dao.TranslatedImgDao;
import com.yexingyi.entity.TranslatedImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void insertTranslatedImg(TranslatedImg file) {
        translatedImgDao.insert(file);
    }
}
