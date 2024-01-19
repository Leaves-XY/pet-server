package com.yexingyi;

import com.yexingyi.common.SendSms;
import com.yexingyi.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetApplicationTests {
    @Autowired
    SendSms sendSms;

    @Autowired
    private UserDao userDao;
    @Test
    void contextLoads() throws Exception {
        System.out.println(userDao.getUserByName("aaa"));
    }



}
