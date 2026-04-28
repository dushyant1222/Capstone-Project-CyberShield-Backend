package com.cybershield.repository;

import com.cybershield.model.CommunityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommunityResponseRepository extends JpaRepository<CommunityResponse, Long> {
    List<CommunityResponse> findByCaseId(String caseId);
}