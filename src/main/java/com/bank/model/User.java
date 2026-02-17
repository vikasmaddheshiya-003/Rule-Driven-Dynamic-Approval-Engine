package com.bank.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class User {

    private Long userId;
    private String name;
    private String password;
    private BigDecimal balance;
    private String email;
    private Long accountNumber;   // ✅ NEW FIELD
    private UserStatus status;
    private Timestamp createdAt;

    public enum UserStatus {
        ACTIVE,
        INACTIVE
    }

    // No-arg constructor
    public User() {}

    // Full constructor
    public User(Long userId, String name, String password, BigDecimal balance,
                String email, Long accountNumber, UserStatus status, Timestamp createdAt) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.email = email;
        this.accountNumber = accountNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

   
    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
