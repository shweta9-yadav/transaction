package com.example.transaction.Model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanResponse {
    private boolean eligible;
    private BigDecimal approvedLoanAmount;
    private Map<String, BigDecimal> emiBreakdown;
    private String reason;

    public LoanResponse(boolean eligible,String reason){
        this.eligible=eligible;
        this.reason=reason;

    }

    public LoanResponse(boolean eligible,BigDecimal approvedLoanAmount,Map<String, BigDecimal> emiBreakdown){
        this.eligible=eligible;
        this.approvedLoanAmount=approvedLoanAmount;
        this.emiBreakdown=emiBreakdown;
    }
    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public BigDecimal getApprovedLoanAmount() {
        return approvedLoanAmount;
    }

    public void setApprovedLoanAmount(BigDecimal approvedLoanAmount) {
        this.approvedLoanAmount = approvedLoanAmount;
    }

    public Map<String, BigDecimal> getEmiBreakdown() {
        return emiBreakdown;
    }

    public void setEmiBreakdown(Map<String, BigDecimal> emiBreakdown) {
        this.emiBreakdown = emiBreakdown;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
