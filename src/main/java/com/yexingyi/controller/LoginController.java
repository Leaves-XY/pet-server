package com.yexingyi.controller;

import com.yexingyi.common.SendSms;
import com.yexingyi.model.ResponseMsg;
import com.yexingyi.utils.ValidateCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author 叶倖燚
 */

@RestController
@Api(tags = "登录接口")
@RequestMapping("/pet/login")
public class LoginController {
    @Autowired
    SendSms sendSms;

    @ApiOperation(value = "此接口只是形式,真正登录接口为/pet/doLogin,注销为/pet/logout,SpringSecurity的规定")
    public ResponseMsg login() {
        return ResponseMsg.error("尚未登录！请登录");
    }

    @ApiOperation(value = "发送短信验证码")
    public ResponseMsg sendSms( String phone,HttpSession session) {
        Integer code=ValidateCodeUtils.generateValidateCode(4);
        session.setAttribute(phone,code);
        try {
            if(phone.equals("15679707018"))sendSms.sentMes(phone,code.toString());
            else{
                return ResponseMsg.ok("发送成功！,您的验证码为:"+code.toString());
            }
        } catch (Exception e) {
            return ResponseMsg.error("发送失败: " + e.getMessage());
        }
        return ResponseMsg.ok("发送成功！请在手机查收");
    }
}
