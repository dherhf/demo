package com.example.controller;

import com.example.dto.desk.DeskResponse;
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

    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    // 获取餐台状态
    @GetMapping("/{id}/status")
    public ResponseEntity<DeskResponse> getDeskStatus(@PathVariable int id) {
        Optional<Desk> desk = deskService.getDeskById(id);
        if (desk.isPresent()) {
            DeskResponse deskResponse = convertToResponse(desk.get());
            return ResponseEntity.ok(deskResponse);
        }
        return ResponseEntity.notFound().build();
    }

    // 打开餐台
    @PostMapping("/{id}/open")
    public ResponseEntity<DeskResponse> openDesk(@PathVariable int id) {
        Optional<Desk> desk = deskService.openDesk(id);
        if (desk.isPresent()) {
            DeskResponse deskResponse = convertToResponse(desk.get());
            return ResponseEntity.ok(deskResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<DeskResponse> closeDesk(@PathVariable int id){
        Optional<Desk> desk = deskService.closeDesk(id);
        if (desk.isPresent()) {
            DeskResponse deskResponse = convertToResponse(desk.get());
            return ResponseEntity.ok(deskResponse);
        }
        return ResponseEntity.notFound().build();
    }

    private DeskResponse convertToResponse(Desk desk) {
        DeskResponse response = new DeskResponse();
        response.setCode(desk.getCode());
        response.setDescription(desk.getDescription());
        response.setCapacity(desk.getCapacity());
        response.setOpen(desk.isOpen());
        return response;
    }
}
