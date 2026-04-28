package com.cybershield.controller;

import com.cybershield.dto.AIRequest;
import com.cybershield.dto.AIResponse;
import com.cybershield.service.AIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/solve")
    public AIResponse solveCyberIssue(@RequestBody AIRequest request) {
        return aiService.analyzeQuestion(request.getQuestion());
    }
}