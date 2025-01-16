package com.example.transaction.Service;

import com.example.transaction.Model.Loan;
import com.example.transaction.Model.LoanResponse;

import java.util.List;

public interface LoanService {
    LoanResponse loaneligiblity(Loan loan);
    List<Loan> loanstatus();
}
