package com.cybershield.controller;

import com.cybershield.model.CommunityResponse;
import com.cybershield.service.CommunityResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "*")
public class CommunityResponseController {

    private final CommunityResponseService service;

    public CommunityResponseController(CommunityResponseService service) {
        this.service = service;
    }

    @PostMapping
    public CommunityResponse addResponse(@RequestBody CommunityResponse response) {
        return service.addResponse(response);
    }

    @GetMapping("/{caseId}")
    public List<CommunityResponse> getResponses(@PathVariable String caseId) {
        return service.getResponsesByCaseId(caseId);
    }
}