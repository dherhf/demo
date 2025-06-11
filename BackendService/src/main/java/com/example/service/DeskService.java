package com.example.service;

import com.example.model.desk.Desk;

import java.util.Optional;

public interface DeskService {
    Optional<Desk> getDeskById(int id);
    Optional<Desk> openDesk(int id);
    Optional<Desk> closeDesk(int id);
}
