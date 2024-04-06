package com.example.classmanager.repository;

import com.example.classmanager.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SummaryRepository extends JpaRepository<CheckIn, Long> {
    @Query(value = "select s from CheckIn s where s.user.id = ?1")
    List<CheckIn> findSummariesByUserId(Long userId);
}
