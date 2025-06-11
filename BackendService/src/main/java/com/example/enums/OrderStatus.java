package com.example.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("待处理"),
    CONFIRMED("已确认"),
    PREPARING("准备中"),
    READY("已完成"),
    SERVED("已上菜"),
    CANCELLED("已取消");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

}
