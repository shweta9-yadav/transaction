package com.example.transaction.Service.ServiceImpl;

import com.example.transaction.Model.Loan;
import com.example.transaction.Model.LoanResponse;
import com.example.transaction.Model.MessageStatus;
import com.example.transaction.Model.Transaction;
import com.example.transaction.Service.LoanService;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {

    private static final int incone = 30000;
    private static final int credit_score = 700;
    String url = "https://prepstripe.com/loan_task_payloads.json";
    RestTemplate restTemplate = new RestTemplate();
    private static final BigDecimal MAX_LOAN_OBLIGATION_PERCENTAGE = new BigDecimal("0.40");


    List<Loan> jsonResponse = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Loan>>() {}
    ).getBody();
    ;

    @Override
    public LoanResponse loaneligiblity(Loan loan) {
        return getstatus(loan);
    }

    @Override
    public List<Loan> loanstatus() {
        List<Loan> data = new ArrayList<>();
        for(Loan datas:jsonResponse){
            Loan dataList = new Loan();
            BeanUtils.copyProperties(datas, dataList);
            dataList.setReason(getstatus(datas).getReason());
            dataList.setApprovedLoanAmount(calculateAverageLoanAmount(datas.getMonthlyIncome(),datas.getExistingLoanObligations()));
            data.add(dataList);
        }
        return data;
    }


    public LoanResponse getstatus(Loan loan){
        if (loan.getMonthlyIncome().intValue()<incone) {
            return new LoanResponse(false, "Monthly income is below the required minimum.");
        }

        BigDecimal incomeThreshold = loan.getMonthlyIncome().multiply(MAX_LOAN_OBLIGATION_PERCENTAGE);
        if (loan.getExistingLoanObligations().compareTo(incomeThreshold) <= 0) {
            return new LoanResponse(false, "Your maximum loan obligation percentage is exceeded.");
        }

        if (loan.getCreditScore()<credit_score) {
            return new LoanResponse(false, "Credit Score is Below 700 not elligible.");
        }

        BigDecimal maxLoanAmount = loan.getMonthlyIncome().multiply(BigDecimal.TEN);
        if (loan.getRequestedLoanAmount().compareTo(maxLoanAmount) > 0) {
            return new LoanResponse(false, "Requested Loan amount cannot exceed 10x the monthly income.");
        }
        Map<String, BigDecimal> emiBreakdown = new HashMap<>();
        emiBreakdown.put("8%", calculateEMI(loan.getRequestedLoanAmount(), BigDecimal.valueOf(0.08)));
        emiBreakdown.put("10%", calculateEMI(loan.getRequestedLoanAmount(), BigDecimal.valueOf(0.10)));
        emiBreakdown.put("12%", calculateEMI(loan.getRequestedLoanAmount(), BigDecimal.valueOf(0.12)));
        return new LoanResponse(true, loan.getRequestedLoanAmount(),emiBreakdown);
    }
    private BigDecimal calculateEMI(BigDecimal loanAmount, BigDecimal interestRate) {

        int numberOfMonths = 12;
        BigDecimal ratePerMonth = interestRate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL128);
        BigDecimal onePlusRate = BigDecimal.ONE.add(ratePerMonth);
        BigDecimal emi = loanAmount.multiply(ratePerMonth)
                .multiply(onePlusRate.pow(numberOfMonths))
                .divide(onePlusRate.pow(numberOfMonths).subtract(BigDecimal.ONE), MathContext.DECIMAL128);
        return emi.setScale(2, RoundingMode.HALF_UP);  // Round to two decimal places
    }
    public static BigDecimal calculateAverageLoanAmount(BigDecimal monthlyIncome, BigDecimal existingLoanObligations) {
        BigDecimal maxLoanAmount = monthlyIncome.multiply(BigDecimal.TEN);

        BigDecimal maxObligations = monthlyIncome.multiply(BigDecimal.valueOf(0.40));
        if (existingLoanObligations.compareTo(maxObligations) > 0) {
            return BigDecimal.ZERO;  // Not eligible for any loan
        }

        BigDecimal remainingObligations = maxObligations.subtract(existingLoanObligations);
        BigDecimal approvedLoanAmount = remainingObligations.min(maxLoanAmount);
        return approvedLoanAmount.setScale(2, RoundingMode.HALF_UP);
    }

}
