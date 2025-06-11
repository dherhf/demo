package com.example.service;

import com.example.model.desk.Desk;
import com.example.repository.DeskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeskService {
    private final DeskRepository deskRepository;

    public DeskService(DeskRepository deskRepository) {
        this.deskRepository = deskRepository;
    }

    public boolean deskIsOpen(int id) {
        Optional<Desk> desk = deskRepository.findById(id);
        return desk.map(Desk::isOpen).orElse(false);
    }

    // 改变餐台开放状态
    public boolean changeDeskStatus(int id, boolean open) {
        Optional<Desk> deskOptional = deskRepository.findById(id);
        if (deskOptional.isEmpty()) {
            return false; // 餐台不存在，返回失败
        }

        Desk desk = deskOptional.get();
        desk.setOpen(open); // 假设Desk实体中有setOpen()方法设置开放状态
        deskRepository.save(desk); // 保存更新后的餐台信息
        return true; // 返回成功
    }


}
