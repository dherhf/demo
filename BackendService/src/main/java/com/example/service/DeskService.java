package com.example.service;

import com.example.dto.DeskDTO;

public interface DeskService {
    DeskDTO getDeskById(Long id);
    DeskDTO openDesk(Long id);
    DeskDTO closeDesk(Long id);
}
