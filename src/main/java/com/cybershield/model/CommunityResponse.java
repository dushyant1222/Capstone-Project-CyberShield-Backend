package com.cybershield.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "community_responses")
public class CommunityResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caseId;

    private String username;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime createdAt;

    public CommunityResponse() {
        this.createdAt = LocalDateTime.now();
    }

    public CommunityResponse(String caseId, String username, String message) {
        this.caseId = caseId;
        this.username = username;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}