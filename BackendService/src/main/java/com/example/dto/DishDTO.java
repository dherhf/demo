package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {
    private Long id;

    @NotBlank(message = "菜品编码不能为空")
    @Size(max = 20, message = "编码长度不能超过20个字符")
    private String code;

    @NotBlank(message = "菜品名称不能为空")
    @Size(max = 50, message = "名称长度不能超过50个字符")
    private String name;

    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    @Size(max = 255, message = "图片URL过长")
    private String imageUrl;

    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    private List<Long> hints;
}
