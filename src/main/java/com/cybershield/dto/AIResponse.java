package com.cybershield.dto;

import java.util.List;

public class AIResponse {

    private String title;
    private List<String> steps;
    private List<String> prevention;

    public AIResponse() {
    }

    public AIResponse(String title, List<String> steps, List<String> prevention) {
        this.title = title;
        this.steps = steps;
        this.prevention = prevention;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<String> getPrevention() {
        return prevention;
    }

    public void setPrevention(List<String> prevention) {
        this.prevention = prevention;
    }
}