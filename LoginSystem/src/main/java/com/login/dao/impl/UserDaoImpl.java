package com.login.dao.impl;

import com.login.dao.UserDao;
import com.login.model.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户数据访问对象的内存实现
 * 在实际项目中，这里可以替换为数据库实现
 */
public class UserDaoImpl implements UserDao {
    
    // 使用ConcurrentHashMap保证线程安全
    private final Map<String, User> users = new ConcurrentHashMap<>();
    
    public UserDaoImpl() {
        // 初始化一些测试用户
        initializeTestUsers();
    }
    
    /**
     * 初始化测试用户
     */
    private void initializeTestUsers() {
        User admin = new User("admin", "admin123", "admin@example.com");
        User testUser = new User("testuser", "test123", "test@example.com");
        
        users.put(admin.getUsername(), admin);
        users.put(testUser.getUsername(), testUser);
    }
    
    @Override
    public boolean save(User user) {
        if (user == null || user.getUsername() == null) {
            return false;
        }
        
        // 检查用户名是否已存在
        if (users.containsKey(user.getUsername())) {
            return false;
        }
        
        users.put(user.getUsername(), user);
        return true;
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(username));
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        
        return users.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst();
    }
    
    @Override
    public boolean update(User user) {
        if (user == null || user.getUsername() == null) {
            return false;
        }
        
        if (users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean delete(String username) {
        if (username == null) {
            return false;
        }
        
        return users.remove(username) != null;
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return username != null && users.containsKey(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        if (email == null) {
            return false;
        }
        
        return users.values().stream()
                .anyMatch(user -> email.equals(user.getEmail()));
    }
}