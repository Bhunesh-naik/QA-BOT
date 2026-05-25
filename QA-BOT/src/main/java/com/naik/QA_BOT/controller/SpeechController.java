package com.naik.QA_BOT.controller;

import com.naik.QA_BOT.service.VoskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/speech")
@CrossOrigin("*")
public class SpeechController {

    private final VoskService service;

    public SpeechController(VoskService service) {
        this.service = service;
    }

    // ðŸ”¥ Upload & Analyze
    @PostMapping("/transcribe")
    public ResponseEntity<?> transcribe(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(service.transcribe(file));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // ðŸ”¥ Get History
    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(service.getAllRecords());
    }

    // ðŸ”¥ Top 5 Questions
    @GetMapping("/top-questions")
    public ResponseEntity<?> getTopQuestions() {
        return ResponseEntity.ok(service.getTopQuestions());
    }
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        return ResponseEntity.ok(service.getDashboardStats());
    }
}
