package com.example.transaction.Service;

import com.example.transaction.Model.MessageStatus;
import com.example.transaction.Model.Transaction;

import java.util.List;

public interface TransactionService {

    MessageStatus postTransaction(Transaction transaction);

    List<Transaction> getTransactionsDetails();
}
