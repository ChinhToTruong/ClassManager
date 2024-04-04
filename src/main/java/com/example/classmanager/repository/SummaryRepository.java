package com.example.classmanager.repository;

import com.example.classmanager.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SummaryRepository extends JpaRepository<Summary, Long> {
    @Query(value = "select s from Summary s where s.user.id = ?1")
    List<Summary> findSummariesByUserId(Long userId);
}
