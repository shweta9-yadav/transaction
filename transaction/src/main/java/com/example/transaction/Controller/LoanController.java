package com.example.transaction.Controller;

import com.example.transaction.Model.Loan;
import com.example.transaction.Model.LoanResponse;
import com.example.transaction.Model.MessageStatus;
import com.example.transaction.Model.Transaction;
import com.example.transaction.Service.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping(value="/api/v1/loan")
@RestController
public class LoanController {

    @Autowired
    LoanService loanService;


    @PostMapping("/loans/eligibility")
    public ResponseEntity<LoanResponse> loaneligible(@RequestBody Loan loan) {
        LoanResponse response = loanService.loaneligiblity(loan);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/loan-statistics")
    public List<Loan> getloanstatue() {
        return loanService.loanstatus();
    }
}
