package com.bank.model;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionHistory {

    private Long transactionId;
    private Long requestId;
    private Long userId;
    private TransactionType transactionType;
    private TxnDirection txnDirection;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private Status status;
    private Timestamp createdAt;

    // --- Enums ---
    public enum TransactionType {
        TRANSFER,
        WITHDRAWAL,
        DEPOSIT
    }

    public enum TxnDirection {
        CREDIT,
        DEBIT
    }

    public enum Status {
        SUCCESS,
        FAILED
    }

    @Override
	public String toString() {
		return "TransactionHistory [transactionId=" + transactionId + ", requestId=" + requestId + ", userId=" + userId
				+ ", transactionType=" + transactionType + ", txnDirection=" + txnDirection + ", amount=" + amount
				+ ", balanceBefore=" + balanceBefore + ", balanceAfter=" + balanceAfter + ", status=" + status
				+ ", createdAt=" + createdAt + "]";
	}

	// --- Getters and Setters ---
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TxnDirection getTxnDirection() {
        return txnDirection;
    }

    public void setTxnDirection(TxnDirection txnDirection) {
        this.txnDirection = txnDirection;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
