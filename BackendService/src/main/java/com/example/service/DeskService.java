package com.example.service;

import com.example.dto.DeskDTO;

import java.util.List;

public interface DeskService {
    List<DeskDTO> getOpenDesks();
    List<DeskDTO> getAllDesks();
    DeskDTO createDesk(DeskDTO deskDTO);
    DeskDTO updateDesk(Long id, DeskDTO deskDTO);
    boolean deleteDeskById(Long id);
    DeskDTO getDeskById(Long id);
    DeskDTO openDesk(Long id);
    DeskDTO closeDesk(Long id);
}
