package com.example.service.impl;

import com.example.dto.DeskDTO;
import com.example.dto.DeskMapper;
import com.example.model.desk.Desk;
import com.example.repository.DeskRepository;
import com.example.service.DeskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeskServiceImpl implements DeskService {

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private DeskMapper deskMapper;

    public DeskDTO getDeskById(Long id) {
        Optional<Desk> desk = deskRepository.findById(id);
        if (desk.isEmpty()){
            throw new EntityNotFoundException("not found");
        }
        return deskMapper.toDTO(desk.get());
    }

    public DeskDTO openDesk(Long id) {
        Optional<Desk> desk = deskRepository.findById(id);
        if (desk.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        desk.get().setOpen(true);
        Desk save = deskRepository.save(desk.get());
        return deskMapper.toDTO(save);
    }

    public DeskDTO closeDesk(Long id) {
        Optional<Desk> desk = deskRepository.findById(id);
        if (desk.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        desk.get().setOpen(false);
        Desk save = deskRepository.save(desk.get());
        return deskMapper.toDTO(save);
    }


}
