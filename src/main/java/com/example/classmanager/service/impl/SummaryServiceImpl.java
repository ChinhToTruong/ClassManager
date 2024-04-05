package com.example.classmanager.service.impl;

import com.example.classmanager.dto.response.SummaryResponse;
import com.example.classmanager.repository.SummaryRepository;
import com.example.classmanager.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {
    private final SummaryRepository summaryRepository;

    @Override
    public SummaryResponse getSummariesByUserId(Long userId) {
        // summary which about the moment user login

        // get summaries
        var summaries = summaryRepository.findSummariesByUserId(userId);

        return SummaryResponse.builder()
                .count(summaries.size())
                .summaries(summaries)
                .build();
    }
}
