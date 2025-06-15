package com.example.controller;

import com.example.dto.DeskDTO;
import com.example.service.DeskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/desk")
@CrossOrigin(origins = "*")
public class DeskController {

    private final DeskService deskService;

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @GetMapping
    public ResponseEntity<List<DeskDTO>> getAllDesks() {
        try {
            List<DeskDTO> responseDTO = deskService.getAllDesks();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<DeskDTO> createDesk(@RequestBody DeskDTO deskDTO) {
        try {
            DeskDTO responseDTO = deskService.createDesk(deskDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<DeskDTO> updateDesk(@PathVariable Long id, @RequestBody DeskDTO deskDTO) {
        try {
            DeskDTO responseDTO = deskService.updateDesk(id, deskDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            // 参数不合法（如ID不匹配）
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            // 资源不存在
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // 其他未预期异常
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/open")
    public ResponseEntity<List<DeskDTO>> getOpenDesks() {
        try {
            List<DeskDTO> responseDTO = deskService.getOpenDesks();
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 获取餐台状态
    @GetMapping("/{id}/status")
    public ResponseEntity<DeskDTO> getDeskStatus(@PathVariable Long id) {
        try {
            DeskDTO responseDTO = deskService.getDeskById(id);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 打开餐台
    @PostMapping("/{id}/open")
    public ResponseEntity<DeskDTO> openDesk(@PathVariable Long id) {
        try {
            DeskDTO responseDTO = deskService.openDesk(id);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<DeskDTO> closeDesk(@PathVariable Long id) {
        try {
            DeskDTO responseDTO = deskService.closeDesk(id);
            return ResponseEntity.ok(responseDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
