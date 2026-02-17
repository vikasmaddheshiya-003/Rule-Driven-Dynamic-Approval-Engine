package com.bank.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Request {

    private Long requestId;
    private Long userId;
    private RequestType requestType;    
    private BigDecimal amount;
    private Status status;               
    private String approvalLevel;
    private Timestamp createdAt;
    private Long accountNumber; 

    public Long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Long accountNumber) { this.accountNumber = accountNumber; }

   
    public enum RequestType {
        TRANSFER,
        WITHDRAWAL,
        DEPOSIT
    }

   
    public enum Status {
        APPROVED,
        PENDING,
        REJECTED
    }

   
    public Request() {}

    public Request(Long requestId, Long userId, RequestType requestType, BigDecimal amount, Status status, String approvalLevel, Timestamp createdAt) {
        this.requestId = requestId;
        this.userId = userId;
        this.requestType = requestType;
        this.amount = amount;
        this.status = status;
        this.approvalLevel = approvalLevel;
        this.createdAt = createdAt;
    }

    
  
    public Request(Long requestId, Long userId, RequestType requestType, BigDecimal amount, Status status,
			String approvalLevel, Timestamp createdAt, Long accountNumber) {
		super();
		this.requestId = requestId;
		this.userId = userId;
		this.requestType = requestType;
		this.amount = amount;
		this.status = status;
		this.approvalLevel = approvalLevel;
		this.createdAt = createdAt;
		this.accountNumber = accountNumber;
	}
    
    
	public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public RequestType getRequestType() { return requestType; }
    public void setRequestType(RequestType requestType) { this.requestType = requestType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getApprovalLevel() { return approvalLevel; }
    public void setApprovalLevel(String approvalLevel) { this.approvalLevel = approvalLevel; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", userId=" + userId +
                ", requestType=" + requestType +
                ", amount=" + amount +
                ", status=" + status +
                ", approvalLevel='" + approvalLevel + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
