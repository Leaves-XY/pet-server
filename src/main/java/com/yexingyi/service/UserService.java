package com.yexingyi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yexingyi.dao.UserDao;
import com.yexingyi.entity.User;
import com.yexingyi.model.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 叶倖燚
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            user=userDao.selectOne(new QueryWrapper<User>().eq("mail",username));
            if (user==null){
                throw new UsernameNotFoundException("用户名或邮箱不存在");
            }
        }
        return user;
    }

    public ResponseMsg registerUser(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            return ResponseMsg.error("用户名已存在");
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setCreateAt(new Date());
        user.setUpdateAt(new Date());
        return ResponseMsg.ok("注册成功");
    }


    public ResponseMsg updateUser(User user) {
        user.update();
        if (userDao.updateById(user)>0){
            return ResponseMsg.ok("更新资料成功");
        }else{
            return ResponseMsg.error("更新资料失败");
        }
    }

    public List<User> getAllUser() {
        return userDao.selectList(null);
    }

    public List<User> getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public int getUserCount(String username) {
        return userDao.getUserCount(username);
    }

    public ResponseMsg deleteUsers(User user) {
        if(userDao.deleteById(user.getId())>0){
            return ResponseMsg.ok("删除成功");
        }else{
            return ResponseMsg.error("删除失败");
        }
    }


}
