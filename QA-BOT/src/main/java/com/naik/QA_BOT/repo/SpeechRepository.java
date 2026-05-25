package com.naik.QA_BOT.repo;

import com.naik.QA_BOT.entity.SpeechRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpeechRepository extends JpaRepository<SpeechRecord, Long> {

}
