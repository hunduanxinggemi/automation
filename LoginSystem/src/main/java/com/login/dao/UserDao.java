package com.login.dao;

import com.login.model.User;
import java.util.Optional;
import java.util.List;

/**
 * 用户数据访问对象接口
 */
public interface UserDao {
    
    /**
     * 保存用户
     */
    boolean save(User user);
    
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 根据邮箱查找用户
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 更新用户信息
     */
    boolean update(User user);
    
    /**
     * 删除用户
     */
    boolean delete(String username);
    
    /**
     * 获取所有用户
     */
    List<User> findAll();
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);
    
    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(String email);
}