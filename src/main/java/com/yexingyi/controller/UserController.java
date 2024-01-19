package com.yexingyi.controller;


import com.yexingyi.entity.User;
import com.yexingyi.model.ResponseMsg;
import com.yexingyi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 叶倖燚
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/pet/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取当前用户信息,前端不用额外传参")
    @PostMapping("/info")
    public User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    @ApiOperation(value = "获取所有用户信息")
    @PostMapping("/list")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @ApiOperation(value = "根据用户名获取用户信息")
    @PostMapping("/getUserByName")
    public List<User> getUsersByName(String name) {
        return userService.getUserByName(name);
    }

    @PostMapping("/register")
    public ResponseMsg userRegister(@RequestBody User user) {
        try {
            return userService.registerUser(user);
        } catch (Exception e) {
            return ResponseMsg.error("注册失败: " + e.getMessage());
        }
    }

    @PostMapping("/edit")
    public ResponseMsg EditUser(@RequestBody User user) {
        try {
            return userService.updateUser(user);
        } catch (Exception e) {
            return ResponseMsg.error("修改失败: " + e.getMessage());
        }
    }

    @PostMapping("/del")
    public ResponseMsg deleteUsers(@RequestBody User user) {
        try {
            return userService.deleteUsers(user);
        } catch (Exception e) {
            return ResponseMsg.error("删除失败: " + e.getMessage());
        }
    }
}
