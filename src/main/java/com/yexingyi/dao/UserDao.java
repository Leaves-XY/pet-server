package com.yexingyi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yexingyi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 叶倖燚
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    // MyBatis-Plus 的 BaseMapper 已经提供了大部分 CRUD 方法，如 insert (saveUser), selectById (findUserById), updateById (updateUser), deleteById (deleteUser)
    // 自定义的方法可以保留，特别是那些复杂的查询

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    @Update("UPDATE user SET password = #{password} WHERE id = #{userId}")
    Integer updatePassword(Integer userId, String password);

    @Select("SELECT * FROM user WHERE username = #{username}")
    List<User> getUserByName(String name);

    // 保留复杂或特定的 SQL 查询
    @Select("SELECT COUNT(*) FROM user WHERE username = #{username}")
    int getUserCount(String username);
}