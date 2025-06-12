package com.example.controller;

import com.example.dto.desk.DeskDTO;
import com.example.dto.desk.DeskMapper;
import com.example.model.desk.Desk;
import com.example.service.DeskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/desk")
@CrossOrigin(origins = "*")
public class DeskController {

    private final DeskService deskService;
    private final DeskMapper deskMapper;

    public DeskController(DeskService deskService, DeskMapper deskMapper) {
        this.deskService = deskService;
        this.deskMapper = deskMapper;
    }

    // 获取餐台状态
    @GetMapping("/{id}/status")
    public ResponseEntity<DeskDTO> getDeskStatus(@PathVariable int id) {
        Optional<Desk> desk = deskService.getDeskById(id);
        if (desk.isPresent()) {
            DeskDTO deskResponse = deskMapper.toDTO(desk.get());
            return ResponseEntity.ok(deskResponse);
        }
        return ResponseEntity.notFound().build();
    }

    // 打开餐台
    @PostMapping("/{id}/open")
    public ResponseEntity<DeskDTO> openDesk(@PathVariable int id) {
        Optional<Desk> desk = deskService.openDesk(id);
        if (desk.isPresent()) {
            DeskDTO deskResponse = deskMapper.toDTO(desk.get());
            return ResponseEntity.ok(deskResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<DeskDTO> closeDesk(@PathVariable int id){
        Optional<Desk> desk = deskService.closeDesk(id);
        if (desk.isPresent()) {
            DeskDTO deskResponse = deskMapper.toDTO(desk.get());
            return ResponseEntity.ok(deskResponse);
        }
        return ResponseEntity.notFound().build();
    }

}
