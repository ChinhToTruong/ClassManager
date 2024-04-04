package com.example.classmanager.service;

import com.example.classmanager.dto.response.SummaryResponse;

public interface SummaryService {
    SummaryResponse getSummariesByUserId(Long userId);
}
