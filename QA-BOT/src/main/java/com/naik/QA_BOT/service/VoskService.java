package com.naik.QA_BOT.service;

import com.naik.QA_BOT.entity.SpeechRecord;
import com.naik.QA_BOT.repo.SpeechRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class VoskService {

    private final SpeechRepository repository;

    public VoskService(SpeechRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> transcribe(MultipartFile file) throws Exception {

        String uploadDir = System.getProperty("user.dir") + "\\uploads\\";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + ".wav";
        File audioFile = new File(uploadDir + fileName);
        file.transferTo(audioFile);

        String cleanPath = uploadDir + "clean_" + fileName;

        ProcessBuilder ffmpeg = new ProcessBuilder(
                "ffmpeg",
                "-i", audioFile.getAbsolutePath(),
                "-vn",
                "-acodec", "pcm_s16le",
                "-ar", "16000",
                "-ac", "1",
                cleanPath
        );

        ffmpeg.redirectErrorStream(true);
        Process ffmpegProcess = ffmpeg.start();
        int ffmpegExitCode = ffmpegProcess.waitFor();
        if (ffmpegExitCode != 0) {
            throw new IllegalStateException("ffmpeg failed to prepare audio");
        }

        File cleanFile = new File(cleanPath);
        String transcript = transcribeWithWhisper(cleanFile);
        if (transcript.isEmpty()) {
            transcript = "No speech detected";
        }

        String text = transcript.toLowerCase();
        int wordCount = text.split(" ").length;

        String sentiment = "NEUTRAL";
        if (text.contains("angry") || text.contains("bad") || text.contains("problem")) {
            sentiment = "NEGATIVE";
        } else if (text.contains("thank") || text.contains("good") || text.contains("great")) {
            sentiment = "POSITIVE";
        }

        int accuracy = 55;

        if (wordCount > 10) accuracy += 5;
        if (wordCount > 25) accuracy += 5;

        if (text.contains("insurance") || text.contains("claim") || text.contains("policy")) {
            accuracy += 5;
        }

        if (containsAny(text, "resolved", "fixed", "done", "completed", "helped", "received", "updated")) {
            accuracy += 20;
        }

        if (containsAny(text, "thank", "good", "great", "happy", "satisfied")) {
            accuracy += 10;
        }

        if (containsAny(text, "sorry", "please", "check", "help", "support")) {
            accuracy += 5;
        }

        if (containsAny(text, "uh", "hmm", "...")) {
            accuracy -= 10;
        }

        if (wordCount < 5) accuracy -= 20;

        if (containsAny(text, "not resolved", "not solved", "still waiting", "no response", "issue", "problem", "delay", "pending")) {
            accuracy -= 25;
        }

        if ("NEGATIVE".equals(sentiment)) {
            accuracy -= 15;
        } else if ("POSITIVE".equals(sentiment)) {
            accuracy += 10;
        }

        accuracy = Math.max(0, Math.min(100, accuracy));

        int agentScore = 50;

        if (text.contains("thank") || text.contains("help") || text.contains("welcome")) {
            agentScore += 20;
        }

        if (text.contains("sorry") || text.contains("please")) {
            agentScore += 10;
        }

        if (text.contains("angry") || text.contains("delay") || text.contains("problem")) {
            agentScore -= 20;
        }

        if (wordCount > 30) agentScore += 10;
        if (wordCount < 5) agentScore -= 20;

        if ("NEGATIVE".equals(sentiment)) {
            agentScore = Math.max(20, agentScore - 30);
        }

        List<String> questions = new ArrayList<>();
        if (text.contains("insurance")) questions.add("insurance related query");
        if (text.contains("claim")) questions.add("claim related query");
        if (text.contains("policy")) questions.add("policy related query");

        List<String> alerts = new ArrayList<>();
        if (text.contains("otp") || text.contains("password")) {
            alerts.add("Sensitive data detected");
        }

        SpeechRecord record = new SpeechRecord();
        record.setTranscript(transcript);
        record.setSentiment(sentiment);
        record.setAgentScore(agentScore);
        record.setAccuracy(accuracy);
        record.setCommonQuestions(questions.toString());
        record.setAlerts(alerts.toString());
        record.setFileName(fileName);
        record.setCreatedAt(LocalDateTime.now());

        repository.save(record);

        Map<String, Object> result = new HashMap<>();
        result.put("transcript", transcript);
        result.put("sentiment", sentiment);
        result.put("agentScore", agentScore);
        result.put("accuracy", accuracy);
        result.put("commonQuestions", questions);
        result.put("alerts", alerts);

        return result;
    }

    public List<SpeechRecord> getAllRecords() {
        return repository.findAll();
    }

    public List<String> getTopQuestions() {

        List<String> all = repository.findAll()
                .stream()
                .map(SpeechRecord::getCommonQuestions)
                .toList();

        Map<String, Integer> countMap = new HashMap<>();

        for (String qList : all) {
            if (qList == null || qList.equals("[]")) continue;

            qList = qList.replace("[", "").replace("]", "");
            String[] questions = qList.split(",");

            for (String q : questions) {
                q = q.trim();
                if (!q.isEmpty()) {
                    countMap.put(q, countMap.getOrDefault(q, 0) + 1);
                }
            }
        }

        return countMap.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }

    public Map<String, Object> getDashboardStats() {

        List<SpeechRecord> records = repository.findAll();

        int totalAudios = records.size();
        int totalAnalysis = records.size();

        int totalScore = 0;
        for (SpeechRecord r : records) {
            totalScore += r.getAgentScore();
        }

        int avgScore = totalAudios > 0 ? totalScore / totalAudios : 0;

        Set<String> uniqueQuestions = new HashSet<>();

        for (SpeechRecord r : records) {
            String qList = r.getCommonQuestions();

            if (qList == null || qList.equals("[]")) continue;

            qList = qList.replace("[", "").replace("]", "");

            String[] arr = qList.split(",");

            for (String q : arr) {
                q = q.trim();
                if (!q.isEmpty()) {
                    uniqueQuestions.add(q);
                }
            }
        }

        int topQuestionsCount = uniqueQuestions.size();

        Map<String, Object> result = new HashMap<>();
        result.put("totalAudios", totalAudios);
        result.put("averageAgentScore", avgScore);
        result.put("totalAnalysis", totalAnalysis);
        result.put("topQuestionsCount", topQuestionsCount);

        return result;
    }

    private String transcribeWithWhisper(File cleanFile) throws Exception {
        String audioPath = cleanFile.getAbsolutePath().replace("\\", "/");
        String pythonScript = System.getProperty("user.dir") + "/whisper_transcribe.py";
        pythonScript = pythonScript.replace("\\", "/");

        ProcessBuilder whisper = new ProcessBuilder(
                "python",
                pythonScript,
                audioPath
        );

        whisper.redirectErrorStream(true);
        Process process = whisper.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    output.append(trimmedLine).append(" ");
                }
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("Whisper transcription failed");
        }

        return output.toString().trim();
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
