package com.example.controller;

import com.example.dto.DeskDTO;
import com.example.service.DeskService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<DeskDTO> closeDesk(@PathVariable Long id){
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
