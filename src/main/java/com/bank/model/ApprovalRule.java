package com.bank.model;

import java.math.BigDecimal;

public class ApprovalRule {

	private Long ruleId;
	private String fieldName;
	private String operator;
	private BigDecimal fieldValue;
	private String approvalLevel;

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public BigDecimal getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(BigDecimal fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getApprovalLevel() {
		return approvalLevel;
	}

	@Override
	public String toString() {
		return "ApprovalRule [ruleId=" + ruleId + ", fieldName=" + fieldName + ", operator=" + operator
				+ ", fieldValue=" + fieldValue + ", approvalLevel=" + approvalLevel + "]";
	}

	public void setApprovalLevel(String approvalLevel) {
		this.approvalLevel = approvalLevel;
	}
}
