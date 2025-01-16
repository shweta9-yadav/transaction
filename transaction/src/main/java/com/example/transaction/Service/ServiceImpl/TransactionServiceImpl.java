package com.example.transaction.Service.ServiceImpl;

import com.example.transaction.Model.MessageStatus;
import com.example.transaction.Model.Transaction;
import com.example.transaction.Service.TransactionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final int TRANSACTION_LIMIT = 100000;
    private static final List<String> BLACKLISTED_ACCOUNTS = Arrays.asList("1234567890", "9876543210");
    private static final String INDIA_IP_RANGE = "India";
    String url = "https://prepstripe.com/transaction_task_payloads.json";
    RestTemplate restTemplate = new RestTemplate();

    List<Transaction> jsonResponse = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Transaction>>() {}
    ).getBody();
    ;

    public MessageStatus postTransaction(Transaction transaction) {
        return getstatus(transaction);
    }

    @Override
    public List<Transaction> getTransactionsDetails() {
        List<Transaction> list = new ArrayList<>();
        for(Transaction data:jsonResponse){
            Transaction dataList = new Transaction();
            BeanUtils.copyProperties(data, dataList);
            dataList.setReason(getstatus(data).getMessage());
            list.add(dataList);
        }

        return list;
    }


    public MessageStatus getstatus(Transaction transaction){
        if (transaction.getAmount() > TRANSACTION_LIMIT) {
            return new MessageStatus("Fraud", "Transaction amount exceeds the limit of ₹1,00,000.");
        }

        List<Transaction> data = jsonResponse.stream()
                .filter(transactions -> (transactions.getAccountNumber().equals(transaction.getAccountNumber()) && transactions.getTransactionTime().isAfter(transaction.getTransactionTime().minusMinutes(5))))
                .toList();
        if (data.size() > 3) {
            return new MessageStatus("Fraud", "More than 3 transactions within 5 minutes.");
        }

        // Rule 3: Blacklisted account
        if (BLACKLISTED_ACCOUNTS.contains(transaction.getAccountNumber())) {
            return new MessageStatus("Fraud", "Transaction from blacklisted account.");
        }

        // Rule 4: IP address outside India
        if (!INDIA_IP_RANGE.equals(transaction.getLocation().getCountry()) && transaction.getLocation().getCountry().equalsIgnoreCase("india")) {
            return new MessageStatus("Fraud", "Transaction initiated from IP address outside India.");
        }

        return new MessageStatus("Success", "Transaction is valid.");
    }
}
