package com.cybershield.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cybershield.model.Complaint;
import com.cybershield.service.ComplaintService;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // 📂 Upload folder path
    private static final String UPLOAD_DIR = "uploads/";

    // 🔥 1. SUBMIT COMPLAINT WITH EVIDENCE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Complaint submitComplaint(
            @RequestParam("crimeType") String crimeType,
            @RequestParam("description") String description,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "evidence", required = false) MultipartFile evidence
    ) {

        Complaint complaint = new Complaint();

        complaint.setCrimeType(crimeType);
        complaint.setDescription(description);
        complaint.setName(name);
        complaint.setEmail(email);
        complaint.setPhone(phone);
        complaint.setLocation(location);

        // FILE HANDLING
        if (evidence != null && !evidence.isEmpty()) {
            try {

                // Create uploads folder if missing
                File uploadFolder = new File(UPLOAD_DIR);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }

                // Unique filename
                String fileName = UUID.randomUUID() + "_" + evidence.getOriginalFilename();

                // Save file
                File destinationFile = new File(UPLOAD_DIR + fileName);
                evidence.transferTo(destinationFile);

                // Store path in DB
                complaint.setEvidencePath(destinationFile.getPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return complaintService.saveComplaint(complaint);
    }

    // 🔍 2. TRACK COMPLAINT
    @GetMapping("/{caseId}")
    public Complaint getComplaint(@PathVariable String caseId) {
        Optional<Complaint> complaint = complaintService.getComplaintByCaseId(caseId);
        return complaint.orElse(null);
    }
}