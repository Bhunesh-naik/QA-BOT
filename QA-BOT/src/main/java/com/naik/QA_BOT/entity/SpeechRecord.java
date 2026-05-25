package com.naik.QA_BOT.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SpeechRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String transcript;

    private String sentiment;
    private int agentScore;
    private int accuracy;

    @Column(length = 1000)
    private String commonQuestions;

    @Column(length = 1000)
    private String alerts;

    private String fileName;
    private LocalDateTime createdAt;
    public SpeechRecord(){

    }
    // ðŸ”¥ GETTERS & SETTERS

    public SpeechRecord(Long id, String transcript, String sentiment, int agentScore, int accuracy, String commonQuestions, String alerts, String fileName, LocalDateTime createdAt) {
        this.id = id;
        this.transcript = transcript;
        this.sentiment = sentiment;
        this.agentScore = agentScore;
        this.accuracy = accuracy;
        this.commonQuestions = commonQuestions;
        this.alerts = alerts;
        this.fileName = fileName;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }

    public String getTranscript() { return transcript; }
    public void setTranscript(String transcript) { this.transcript = transcript; }

    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }

    public int getAgentScore() { return agentScore; }
    public void setAgentScore(int agentScore) { this.agentScore = agentScore; }

    public int getAccuracy() { return accuracy; }
    public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

    public String getCommonQuestions() { return commonQuestions; }
    public void setCommonQuestions(String commonQuestions) { this.commonQuestions = commonQuestions; }

    public String getAlerts() { return alerts; }
    public void setAlerts(String alerts) { this.alerts = alerts; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
