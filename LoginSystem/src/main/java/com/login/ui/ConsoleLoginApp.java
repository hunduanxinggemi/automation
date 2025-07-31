package com.login.ui;

import com.login.model.User;
import com.login.service.LoginService;
import java.util.Scanner;
import java.util.Optional;

/**
 * 控制台登录应用程序
 */
public class ConsoleLoginApp {
    
    private final LoginService loginService;
    private final Scanner scanner;
    private User currentUser;
    
    public ConsoleLoginApp() {
        this.loginService = new LoginService();
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
    }
    
    /**
     * 应用程序主入口
     */
    public void start() {
        System.out.println("=================================");
        System.out.println("     欢迎使用登录系统");
        System.out.println("=================================");
        
        while (true) {
            if (currentUser == null) {
                showMainMenu();
            } else {
                showUserMenu();
            }
        }
    }
    
    /**
     * 显示主菜单（未登录状态）
     */
    private void showMainMenu() {
        System.out.println("\n请选择操作：");
        System.out.println("1. 登录");
        System.out.println("2. 注册");
        System.out.println("3. 退出");
        System.out.print("请输入选项（1-3）：");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                handleRegister();
                break;
            case "3":
                System.out.println("感谢使用，再见！");
                System.exit(0);
                break;
            default:
                System.out.println("无效选项，请重新选择！");
        }
    }
    
    /**
     * 显示用户菜单（已登录状态）
     */
    private void showUserMenu() {
        System.out.println("\n=================================");
        System.out.println("欢迎，" + currentUser.getUsername() + "！");
        System.out.println("=================================");
        System.out.println("1. 查看个人信息");
        System.out.println("2. 注销登录");
        System.out.println("3. 退出程序");
        System.out.print("请输入选项（1-3）：");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                showUserInfo();
                break;
            case "2":
                handleLogout();
                break;
            case "3":
                System.out.println("感谢使用，再见！");
                System.exit(0);
                break;
            default:
                System.out.println("无效选项，请重新选择！");
        }
    }
    
    /**
     * 处理用户登录
     */
    private void handleLogin() {
        System.out.println("\n=== 用户登录 ===");
        System.out.print("请输入用户名：");
        String username = scanner.nextLine().trim();
        
        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        
        LoginService.LoginResult result = loginService.login(username, password);
        
        if (result.isSuccess()) {
            currentUser = result.getUser();
            System.out.println("✓ " + result.getMessage());
            System.out.println("登录时间：" + currentUser.getLastLoginAt());
        } else {
            System.out.println("✗ " + result.getMessage());
        }
    }
    
    /**
     * 处理用户注册
     */
    private void handleRegister() {
        System.out.println("\n=== 用户注册 ===");
        System.out.print("请输入用户名：");
        String username = scanner.nextLine().trim();
        
        System.out.print("请输入密码（至少6位）：");
        String password = scanner.nextLine();
        
        System.out.print("请输入邮箱：");
        String email = scanner.nextLine().trim();
        
        LoginService.RegistrationResult result = loginService.register(username, password, email);
        
        if (result.isSuccess()) {
            System.out.println("✓ " + result.getMessage());
            System.out.println("现在您可以使用新账户登录了！");
        } else {
            System.out.println("✗ " + result.getMessage());
        }
    }
    
    /**
     * 显示用户信息
     */
    private void showUserInfo() {
        System.out.println("\n=== 个人信息 ===");
        System.out.println("用户名：" + currentUser.getUsername());
        System.out.println("邮箱：" + currentUser.getEmail());
        System.out.println("注册时间：" + currentUser.getCreatedAt());
        System.out.println("最后登录：" + currentUser.getLastLoginAt());
        System.out.println("账户状态：" + (currentUser.isActive() ? "正常" : "已禁用"));
    }
    
    /**
     * 处理用户注销
     */
    private void handleLogout() {
        String username = currentUser.getUsername();
        currentUser = null;
        System.out.println("用户 " + username + " 已成功注销！");
    }
    
    /**
     * 程序入口点
     */
    public static void main(String[] args) {
        // 显示测试账户信息
        System.out.println("系统已预置测试账户：");
        System.out.println("用户名：admin，密码：admin123");
        System.out.println("用户名：testuser，密码：test123");
        System.out.println();
        
        ConsoleLoginApp app = new ConsoleLoginApp();
        app.start();
    }
}