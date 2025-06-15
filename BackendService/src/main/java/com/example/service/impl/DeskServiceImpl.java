package com.example.service.impl;

import com.example.dto.DeskDTO;
import com.example.dto.DeskMapper;
import com.example.model.desk.Desk;
import com.example.repository.DeskRepository;
import com.example.service.DeskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeskServiceImpl implements DeskService {

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private DeskMapper deskMapper;

    public List<DeskDTO> getOpenDesks() {
        List<Desk> desks = deskRepository.findByOpen(true);
        return desks.stream().map(deskMapper::toDTO).collect(Collectors.toList());
    }

    public List<DeskDTO> getAllDesks() {
        List<Desk> desks = deskRepository.findAll();
        return desks.stream().map(deskMapper::toDTO).collect(Collectors.toList());
    }

    public DeskDTO createDesk(DeskDTO deskDTO) {
        deskDTO.setId(null);
        Desk save = deskRepository.save(deskMapper.toEntity(deskDTO));
        return deskMapper.toDTO(save);
    }

    public DeskDTO updateDesk(Long id ,DeskDTO deskDTO) {
        // 1. 校验URL ID与DTO ID是否一致
        if (!id.equals(deskDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }

        // 2. 检查ID是否存在
        if (!deskRepository.existsById(id)) {
            throw new EntityNotFoundException("not found");
        }
        Desk save = deskRepository.save(deskMapper.toEntity(deskDTO));
        return deskMapper.toDTO(save);
    }

    public DeskDTO getDeskById(Long id) {
        Optional<Desk> desk = deskRepository.findById(id);
        if (desk.isEmpty()) {
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
