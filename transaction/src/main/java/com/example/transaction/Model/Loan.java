package com.example.transaction.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Loan {
    private String userId;
    private BigDecimal monthlyIncome;
    private BigDecimal existingLoanObligations;
    private Integer creditScore;
    private BigDecimal requestedLoanAmount;
    private String reason;
    private  BigDecimal approvedLoanAmount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getExistingLoanObligations() {
        return existingLoanObligations;
    }

    public void setExistingLoanObligations(BigDecimal existingLoanObligations) {
        this.existingLoanObligations = existingLoanObligations;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public BigDecimal getRequestedLoanAmount() {
        return requestedLoanAmount;
    }

    public void setRequestedLoanAmount(BigDecimal requestedLoanAmount) {
        this.requestedLoanAmount = requestedLoanAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getApprovedLoanAmount() {
        return approvedLoanAmount;
    }

    public void setApprovedLoanAmount(BigDecimal approvedLoanAmount) {
        this.approvedLoanAmount = approvedLoanAmount;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "userId='" + userId + '\'' +
                ", monthlyIncome=" + monthlyIncome +
                ", existingLoanObligations=" + existingLoanObligations +
                ", creditScore=" + creditScore +
                ", requestedLoanAmount=" + requestedLoanAmount +
                ", reason='" + reason + '\'' +
                ", approvedLoanAmount=" + approvedLoanAmount +
                '}';
    }
}
