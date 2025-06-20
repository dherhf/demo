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

    @DeleteMapping("{id}")
    public ResponseEntity<DeskDTO> deleteDeskById(@PathVariable Long id) {
        boolean deleted = deskService.deleteDeskById(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}
