package com.example.enums;

import lombok.Getter;

@Getter
public enum Priority {
    LOW("低"),
    MEDIUM("中"),
    HIGH("高"),
    URGENT("紧急");

    private final String description;

    Priority(String description) {
        this.description = description;
    }

}
