package com.login.model;

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用户模型类
 */
public class User {
    private String username;
    private String passwordHash;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private boolean isActive;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    public User(String username, String password, String email) {
        this();
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.email = email;
    }

    /**
     * 使用SHA-256对密码进行哈希加密
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256算法不可用", e);
        }
    }

    /**
     * 验证密码
     */
    public boolean verifyPassword(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    /**
     * 更新最后登录时间
     */
    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                ", isActive=" + isActive +
                '}';
    }
}