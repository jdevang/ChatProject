package com.psu.dxj5305.chatproject.models;


import java.util.Date;

public class Message {
    String message;
    String sentBy;
    String sentTo;
    Date sentAt;

    public Message(){}
    public Message(String message, String sentBy, String sentTo, Date sentAt) {
        this.message = message;
        this.sentBy = sentBy;
        this.sentTo = sentTo;
        this.sentAt = sentAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
