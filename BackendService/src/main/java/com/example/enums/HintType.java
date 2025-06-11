package com.example.enums;

import lombok.Getter;

@Getter
public enum HintType {
    SPICY("辣度"),
    ALLERGEN("过敏原"),
    SPECIAL("特色"),
    TEMPERATURE("温度"),
    DIETARY("饮食偏好");

    private final String description;

    HintType(String description) {
        this.description = description;
    }

}
