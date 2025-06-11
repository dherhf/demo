package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.service.DeskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/desk")
@CrossOrigin(origins = "*")
public class DeskController {

    private final DeskService deskService;

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    // 判断餐台是否开发
    @GetMapping("is-open/{id}")
    public ResponseEntity<ApiResponse<Boolean>> isDeskOpen (@PathVariable int id) {
        try {
            boolean isOpen = deskService.deskIsOpen(id);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "查询餐台状态成功", isOpen)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                    new ApiResponse<>(false, "查询餐台状态失败:", false)
            );
        }
    }

    // 改变餐台开放状态
    @PutMapping("change-status/{id}")
    public ResponseEntity<ApiResponse<Boolean>> changeDeskStatus(
            @PathVariable int id,
            @RequestParam boolean open) {
        try {
            boolean result = deskService.changeDeskStatus(id, open);
            if (result) {
                return ResponseEntity.ok(
                        new ApiResponse<>(true, "餐台状态更新成功", open)
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "餐台不存在，更新失败", false)
                );
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "更新餐台状态失败" , false)
            );
        }
    }
}
