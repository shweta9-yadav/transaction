package com.example.transaction.Controller;

import com.example.transaction.Model.Location;
import com.example.transaction.Model.MessageStatus;
import com.example.transaction.Model.Transaction;
import com.example.transaction.Service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import netscape.javascript.JSObject;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.List;

//@RequestMapping(value="/api/v1/transaction")
    @RestController
    public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions/process")
    public ResponseEntity<MessageStatus> processTransaction(@RequestBody Transaction transaction) {
        MessageStatus response = transactionService.postTransaction(transaction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/flagged-transactions")
    public List<Transaction> getTransactions() {
        return transactionService.getTransactionsDetails();
    }
    }

