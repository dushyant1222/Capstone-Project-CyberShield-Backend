package com.cybershield.service;

import com.cybershield.dto.AIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.api.url}")
    private String groqApiUrl;

    private final RestTemplate restTemplate;

    public AIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AIResponse analyzeQuestion(String question) {

        try {

            String prompt = """
                    You are a cybersecurity expert.
                    Analyze the user's cybercrime issue and return:
                    1. A short title
                    2. 5 recovery steps
                    3. 4 prevention tips

                    Format clearly like:
                    Title: ...
                    1. ...
                    2. ...
                    3. ...
                    4. ...
                    5. ...
                    - ...
                    - ...
                    - ...
                    - ...

                    User Issue:
                    """ + question;

            // GROQ REQUEST BODY
            Map<String, Object> requestBody = Map.of(
                    "model", "llama-3.3-70b-versatile",
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt
                            )
                    ),
                    "temperature", 0.4
            );

            // HEADERS
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey.trim());

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(requestBody, headers);

            // NO ?key= for GROQ
            ResponseEntity<Map> response = restTemplate.exchange(
                    groqApiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            // GROQ RESPONSE PARSING
            List<Map<String, Object>> choices =
                    (List<Map<String, Object>>) response.getBody().get("choices");

            Map<String, Object> message =
                    (Map<String, Object>) choices.get(0).get("message");

            String aiText = message.get("content").toString();

            System.out.println("GROQ RESPONSE: " + aiText);

            // FORMAT RESPONSE
            List<String> lines = Arrays.stream(aiText.split("\n"))
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .toList();

            String title = lines.size() > 0
                    ? lines.get(0).replace("Title:", "").trim()
                    : "Cybercrime Recovery Plan";

            List<String> steps = lines.stream()
                    .filter(line -> line.matches("^[0-9].*"))
                    .limit(5)
                    .toList();

            List<String> prevention = lines.stream()
                    .filter(line -> line.startsWith("-") || line.startsWith("•"))
                    .limit(4)
                    .toList();

            // FALLBACK IF AI FORMAT BAD
            if (steps.isEmpty()) {
                steps = List.of(
                        "Document the incident immediately.",
                        "Secure all affected accounts.",
                        "Contact bank/platform support.",
                        "Report to cybercrime.gov.in.",
                        "Monitor for further suspicious activity."
                );
            }

            if (prevention.isEmpty()) {
                prevention = List.of(
                        "Use strong passwords.",
                        "Enable 2FA.",
                        "Avoid suspicious links.",
                        "Keep software updated."
                );
            }

            return new AIResponse(title, steps, prevention);

        } catch (Exception e) {

            e.printStackTrace();

            // SMART FALLBACK SYSTEM
            String lower = question.toLowerCase();

            if (lower.contains("upi") || lower.contains("payment") || lower.contains("money") || lower.contains("transaction")) {
                return new AIResponse(
                        "UPI / Payment Fraud Recovery Plan",
                        List.of(
                                "Call your bank fraud helpline immediately.",
                                "Block your UPI ID and freeze linked accounts.",
                                "Report complaint on cybercrime.gov.in.",
                                "Report fraudulent UPI ID to NPCI.",
                                "File FIR with transaction details."
                        ),
                        List.of(
                                "Never share OTP or UPI PIN.",
                                "Verify collect requests carefully.",
                                "Use official apps only.",
                                "Set transaction limits."
                        )
                );
            }

            else if (lower.contains("phishing") || lower.contains("link") || lower.contains("email")) {
                return new AIResponse(
                        "Phishing / Social Engineering Recovery Plan",
                        List.of(
                                "Stop clicking suspicious links.",
                                "Change passwords immediately.",
                                "Enable 2FA.",
                                "Report phishing source.",
                                "Monitor financial accounts."
                        ),
                        List.of(
                                "Verify sender identity.",
                                "Avoid suspicious links.",
                                "Use password managers.",
                                "Keep systems updated."
                        )
                );
            }

            else {
                return new AIResponse(
                        "General Cybercrime Recovery Plan",
                        List.of(
                                "Document all evidence.",
                                "Secure compromised accounts.",
                                "Enable 2FA.",
                                "Report on cybercrime.gov.in.",
                                "Contact relevant authorities."
                        ),
                        List.of(
                                "Use strong passwords.",
                                "Keep software updated.",
                                "Avoid suspicious links.",
                                "Monitor accounts regularly."
                        )
                );
            }
        }
    }
}