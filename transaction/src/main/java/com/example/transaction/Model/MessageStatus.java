package com.example.transaction.Model;

public class MessageStatus {
    private String status;
    private String message;

    public MessageStatus(){}

    public MessageStatus(
            String status,
    String message
    ){
        super();
        this.message= message;
        this.status= status;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
