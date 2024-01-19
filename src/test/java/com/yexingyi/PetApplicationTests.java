package com.yexingyi;

import com.yexingyi.common.SendSms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetApplicationTests {
    @Autowired
    SendSms sendSms;
    @Test
    void contextLoads() throws Exception {
        sendSms.sentMes("18070290539","123456");
    }

}
