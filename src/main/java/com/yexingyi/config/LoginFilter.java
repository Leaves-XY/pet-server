package com.yexingyi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yexingyi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登录过滤器，用于处理用户登录请求
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 获取验证码
        // String verifyCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        // request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);

        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            Map<String, String> loginData = new HashMap<>(16);
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String username = loginData.get("username");
            String password = loginData.get("password");
            String inputCode = loginData.get("code");

            if (inputCode != null) {
                // 验证码登录逻辑
                String sessionCode = (String) request.getSession().getAttribute(username);
                if (inputCode.equals(sessionCode)) {
                    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, null);
                    setDetails(request, authRequest);
                    return this.getAuthenticationManager().authenticate(authRequest);
                } else {
                    throw new AuthenticationServiceException("验证码不正确");
                }
            } else if (password != null) {
                // 账号密码登录逻辑
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } else {
                throw new AuthenticationServiceException("缺少必要的登录信息");
            }
        } else {
            // 处理普通表单提交的登录请求，继续调用父类的方法
            // checkCode(request.getParameter("code"), verifyCode);
            return super.attemptAuthentication(request, response);
        }
    }

    /**
     * 验证验证码是否正确
     *
     * @param code       用户输入的验证码
     * @param verifyCode 系统生成的验证码
     */
    // public void checkCode(String code, String verifyCode) {
    //     if (code == null || verifyCode == null || "".equals(code) || !verifyCode.equalsIgnoreCase(code)) {
    //         // 验证码不正确
    //         throw new AuthenticationServiceException("验证码不正确");
    //     }
    // }
}