package com.cybershield.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cybershield.model.Complaint;
import com.cybershield.service.ComplaintService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private ComplaintService complaintService;

    // 📋 1. GET ALL COMPLAINTS
    @GetMapping("/complaints")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    // 🔄 2. UPDATE COMPLAINT STATUS
    @PutMapping("/complaints/{caseId}/status")
    public Complaint updateComplaintStatus(
            @PathVariable String caseId,
            @RequestParam String status
    ) {
        return complaintService.updateComplaintStatus(caseId, status);
    }
}