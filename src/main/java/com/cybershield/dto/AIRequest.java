package com.cybershield.dto;

public class AIRequest {

    private String question;

    public AIRequest() {
    }

    public AIRequest(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    
    
}