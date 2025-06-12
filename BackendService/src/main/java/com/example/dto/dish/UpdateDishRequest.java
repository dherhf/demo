package com.example.dto.dish;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateDishRequest {
    @NotNull(message = "ID不能为空")
    private Long id;

    @Size(max = 20, message = "编码长度不能超过20个字符")
    private String code;

    @Size(max = 50, message = "名称长度不能超过50个字符")
    private String name;

    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    @Size(max = 255, message = "图片URL过长")
    private String imageUrl;

    private Long  categoryId;
}
