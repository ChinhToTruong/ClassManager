package com.example.classmanager.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class RoomRequest {

    private Long id;

    private String name;
    private String description;

    Set<Long> teachersId;
    Set<Long> studentsId;
}
