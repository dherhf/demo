package com.example.service.impl;

import com.example.model.desk.Desk;
import com.example.repository.DeskRepository;
import com.example.service.DeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeskServiceImpl implements DeskService {

    @Autowired
    private DeskRepository deskRepository;

    public Optional<Desk> getDeskById(int id) {
        return deskRepository.findById((long) id);
    }

    public Optional<Desk> openDesk(int id) {
        Optional<Desk> Desk = deskRepository.findById((long) id);
        if (Desk.isPresent()) {
            Desk oldDesk = Desk.get();
            oldDesk.setOpen(true);
            deskRepository.save(oldDesk);
        }

        return deskRepository.findById((long) id);
    }

    public Optional<Desk> closeDesk(int id) {
        Optional<Desk> Desk = deskRepository.findById((long) id);
        if (Desk.isPresent()) {
            Desk oldDesk = Desk.get();
            oldDesk.setOpen(false);
            deskRepository.save(oldDesk);
        }

        return deskRepository.findById((long) id);
    }


}
