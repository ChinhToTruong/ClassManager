package com.example.classmanager.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseRoomInfoRequest {
    private String name;
    private String description;
}
