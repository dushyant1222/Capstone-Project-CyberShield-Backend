package com.cybershield.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybershield.model.Complaint;
import com.cybershield.repository.ComplaintRepository;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    // 🔥 1. SAVE COMPLAINT
    public Complaint saveComplaint(Complaint complaint) {

        // Generate Case ID only if new complaint
        if (complaint.getCaseId() == null || complaint.getCaseId().isEmpty()) {
            String caseId = "CYB" + System.currentTimeMillis();
            complaint.setCaseId(caseId);
        }

        return complaintRepository.save(complaint);
    }

    // 🔍 2. GET COMPLAINT BY CASE ID
    public Optional<Complaint> getComplaintByCaseId(String caseId) {
        return complaintRepository.findByCaseId(caseId);
    }

    // 📋 3. GET ALL COMPLAINTS (ADMIN)
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // 🔄 4. UPDATE STATUS (ADMIN)
    public Complaint updateComplaintStatus(String caseId, String newStatus) {

        Optional<Complaint> optionalComplaint =
                complaintRepository.findByCaseId(caseId);

        if (optionalComplaint.isPresent()) {

            Complaint complaint = optionalComplaint.get();

            complaint.setStatus(newStatus.toUpperCase());

            return complaintRepository.save(complaint);
        }

        return null;
    }
}