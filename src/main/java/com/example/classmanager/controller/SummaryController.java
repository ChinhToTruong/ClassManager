package com.example.classmanager.controller;


import com.example.classmanager.dto.response.ApiResponse;
import com.example.classmanager.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
public class SummaryController {
    private final SummaryService summaryService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getSummariesByUserId(@PathVariable("id") Long id){
        return ApiResponse.build()
                .withData(summaryService.getSummariesByUserId(id))
                .withSuccess(true)
                .withMessage("Success.")
                .withHttpStatus(HttpStatus.OK)
                .toEntity();
    }
}
