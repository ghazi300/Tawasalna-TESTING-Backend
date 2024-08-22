package com.ipactconsult.tawasalnabackendapp.payload.request;

public class Message {
    private String text;

    private String to;

    public String getText() {
        return text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}