package com.cybershield.service;

import com.cybershield.model.CommunityResponse;
import com.cybershield.repository.CommunityResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityResponseService {

    private final CommunityResponseRepository repository;

    public CommunityResponseService(CommunityResponseRepository repository) {
        this.repository = repository;
    }

    public CommunityResponse addResponse(CommunityResponse response) {
        return repository.save(response);
    }

    public List<CommunityResponse> getResponsesByCaseId(String caseId) {
        return repository.findByCaseId(caseId);
    }
}