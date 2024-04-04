package com.example.classmanager.dto.response;

import com.example.classmanager.entity.Summary;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponse {
    private long count;
    List<Summary> summaries;
}
