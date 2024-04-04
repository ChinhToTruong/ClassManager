package com.example.classmanager.entity.enums;

import java.util.Arrays;
import java.util.List;


public enum EGender {
    MALE("Male"),
    FEMALE("Female");

    private final String name;
    EGender(String value){
        this.name = value;
    }

    public String getName() {
        return name;
    }
    public List<EGender> getAll(){
        return Arrays.asList(EGender.values());
    }
}
