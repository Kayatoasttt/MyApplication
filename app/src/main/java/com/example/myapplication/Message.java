package com.example.myapplication;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    private String dateTime;

    public Message() {

    }

    public Message(String sender, String receiver, String content, String dateTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.dateTime = dateTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
