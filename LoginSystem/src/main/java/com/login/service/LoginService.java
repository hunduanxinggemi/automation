package com.login.service;

import com.login.dao.UserDao;
import com.login.dao.impl.UserDaoImpl;
import com.login.model.User;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 登录服务类
 */
public class LoginService {
    
    private final UserDao userDao;
    
    // 邮箱格式验证正则表达式
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    public LoginService() {
        this.userDao = new UserDaoImpl();
    }
    
    public LoginService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    public LoginResult login(String username, String password) {
        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            return new LoginResult(false, "用户名不能为空", null);
        }
        
        if (password == null || password.isEmpty()) {
            return new LoginResult(false, "密码不能为空", null);
        }
        
        // 查找用户
        Optional<User> userOptional = userDao.findByUsername(username.trim());
        if (!userOptional.isPresent()) {
            return new LoginResult(false, "用户名或密码错误", null);
        }
        
        User user = userOptional.get();
        
        // 检查用户是否激活
        if (!user.isActive()) {
            return new LoginResult(false, "账户已被禁用", null);
        }
        
        // 验证密码
        if (!user.verifyPassword(password)) {
            return new LoginResult(false, "用户名或密码错误", null);
        }
        
        // 更新最后登录时间
        user.updateLastLogin();
        userDao.update(user);
        
        return new LoginResult(true, "登录成功", user);
    }
    
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 注册结果
     */
    public RegistrationResult register(String username, String password, String email) {
        // 参数验证
        if (username == null || username.trim().isEmpty()) {
            return new RegistrationResult(false, "用户名不能为空");
        }
        
        if (password == null || password.length() < 6) {
            return new RegistrationResult(false, "密码长度不能少于6位");
        }
        
        if (email == null || !isValidEmail(email)) {
            return new RegistrationResult(false, "邮箱格式不正确");
        }
        
        username = username.trim();
        email = email.trim();
        
        // 检查用户名是否已存在
        if (userDao.existsByUsername(username)) {
            return new RegistrationResult(false, "用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (userDao.existsByEmail(email)) {
            return new RegistrationResult(false, "邮箱已被注册");
        }
        
        // 创建新用户
        User newUser = new User(username, password, email);
        
        // 保存用户
        if (userDao.save(newUser)) {
            return new RegistrationResult(true, "注册成功");
        } else {
            return new RegistrationResult(false, "注册失败，请稍后重试");
        }
    }
    
    /**
     * 验证邮箱格式
     */
    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * 根据用户名查找用户
     */
    public Optional<User> findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
    /**
     * 登录结果类
     */
    public static class LoginResult {
        private final boolean success;
        private final String message;
        private final User user;
        
        public LoginResult(boolean success, String message, User user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public User getUser() {
            return user;
        }
    }
    
    /**
     * 注册结果类
     */
    public static class RegistrationResult {
        private final boolean success;
        private final String message;
        
        public RegistrationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
    }
}