package com.login.service;

import com.login.dao.UserDao;
import com.login.dao.impl.UserDaoImpl;
import com.login.model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 登录服务测试类
 */
public class LoginServiceTest {
    
    private LoginService loginService;
    
    @Before
    public void setUp() {
        loginService = new LoginService();
    }
    
    @Test
    public void testLoginWithValidCredentials() {
        // 测试使用有效凭据登录
        LoginService.LoginResult result = loginService.login("admin", "admin123");
        
        assertTrue("登录应该成功", result.isSuccess());
        assertEquals("登录成功", result.getMessage());
        assertNotNull("用户对象不应为空", result.getUser());
        assertEquals("admin", result.getUser().getUsername());
    }
    
    @Test
    public void testLoginWithInvalidUsername() {
        // 测试使用无效用户名登录
        LoginService.LoginResult result = loginService.login("nonexistent", "password");
        
        assertFalse("登录应该失败", result.isSuccess());
        assertEquals("用户名或密码错误", result.getMessage());
        assertNull("用户对象应为空", result.getUser());
    }
    
    @Test
    public void testLoginWithInvalidPassword() {
        // 测试使用无效密码登录
        LoginService.LoginResult result = loginService.login("admin", "wrongpassword");
        
        assertFalse("登录应该失败", result.isSuccess());
        assertEquals("用户名或密码错误", result.getMessage());
        assertNull("用户对象应为空", result.getUser());
    }
    
    @Test
    public void testLoginWithEmptyUsername() {
        // 测试空用户名
        LoginService.LoginResult result = loginService.login("", "password");
        
        assertFalse("登录应该失败", result.isSuccess());
        assertEquals("用户名不能为空", result.getMessage());
    }
    
    @Test
    public void testLoginWithNullUsername() {
        // 测试null用户名
        LoginService.LoginResult result = loginService.login(null, "password");
        
        assertFalse("登录应该失败", result.isSuccess());
        assertEquals("用户名不能为空", result.getMessage());
    }
    
    @Test
    public void testLoginWithEmptyPassword() {
        // 测试空密码
        LoginService.LoginResult result = loginService.login("admin", "");
        
        assertFalse("登录应该失败", result.isSuccess());
        assertEquals("密码不能为空", result.getMessage());
    }
    
    @Test
    public void testRegisterWithValidData() {
        // 测试使用有效数据注册
        LoginService.RegistrationResult result = loginService.register(
            "newuser", "password123", "newuser@example.com");
        
        assertTrue("注册应该成功", result.isSuccess());
        assertEquals("注册成功", result.getMessage());
    }
    
    @Test
    public void testRegisterWithExistingUsername() {
        // 测试使用已存在的用户名注册
        LoginService.RegistrationResult result = loginService.register(
            "admin", "password123", "newemail@example.com");
        
        assertFalse("注册应该失败", result.isSuccess());
        assertEquals("用户名已存在", result.getMessage());
    }
    
    @Test
    public void testRegisterWithExistingEmail() {
        // 测试使用已存在的邮箱注册
        LoginService.RegistrationResult result = loginService.register(
            "newuser2", "password123", "admin@example.com");
        
        assertFalse("注册应该失败", result.isSuccess());
        assertEquals("邮箱已被注册", result.getMessage());
    }
    
    @Test
    public void testRegisterWithShortPassword() {
        // 测试密码过短
        LoginService.RegistrationResult result = loginService.register(
            "newuser3", "123", "user3@example.com");
        
        assertFalse("注册应该失败", result.isSuccess());
        assertEquals("密码长度不能少于6位", result.getMessage());
    }
    
    @Test
    public void testRegisterWithInvalidEmail() {
        // 测试无效邮箱格式
        LoginService.RegistrationResult result = loginService.register(
            "newuser4", "password123", "invalid-email");
        
        assertFalse("注册应该失败", result.isSuccess());
        assertEquals("邮箱格式不正确", result.getMessage());
    }
    
    @Test
    public void testRegisterWithEmptyUsername() {
        // 测试空用户名注册
        LoginService.RegistrationResult result = loginService.register(
            "", "password123", "user@example.com");
        
        assertFalse("注册应该失败", result.isSuccess());
        assertEquals("用户名不能为空", result.getMessage());
    }
    
    @Test
    public void testFindUserByUsername() {
        // 测试根据用户名查找用户
        assertTrue("应该找到admin用户", 
            loginService.findUserByUsername("admin").isPresent());
        
        assertFalse("不应该找到不存在的用户", 
            loginService.findUserByUsername("nonexistent").isPresent());
    }
}