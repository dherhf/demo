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

    /**
     * 获取所有处于开放状态的桌子信息。
     *
     * @return 开放状态桌子的数据传输对象列表
     */
    public List<DeskDTO> getOpenDesks() {
        List<Desk> desks = deskRepository.findByOpen(true);
        return desks.stream().map(deskMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * 获取所有桌子的信息。
     *
     * @return 所有桌子的数据传输对象列表
     */
    public List<DeskDTO> getAllDesks() {
        List<Desk> desks = deskRepository.findAll();
        return desks.stream().map(deskMapper::toDTO).collect(Collectors.toList());
    }


    /**
     * 创建一张新桌子。
     *
     * @param deskDTO 桌子的数据传输对象，包含新桌子的信息
     * @return 新创建桌子的数据传输对象
     */
    public DeskDTO createDesk(DeskDTO deskDTO) {
        deskDTO.setId(null);
        Desk save = deskRepository.save(deskMapper.toEntity(deskDTO));
        return deskMapper.toDTO(save);
    }

    /**
     * 根据ID更新桌子信息。
     *
     * @param id 要更新的桌子的ID
     * @param deskDTO 包含更新信息的桌子数据传输对象
     * @return 更新后桌子的数据传输对象
     * @throws IllegalArgumentException 当URL中的ID与DTO中的ID不一致时抛出
     * @throws EntityNotFoundException 当指定ID的桌子不存在时抛出
     */
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

    /**
     * 根据ID获取桌子信息。
     *
     * @param id 要查询的桌子的ID
     * @return 指定ID桌子的数据传输对象
     * @throws EntityNotFoundException 当指定ID的桌子不存在时抛出
     */
    public DeskDTO getDeskById(Long id) {
        Optional<Desk> desk = deskRepository.findById(id);
        if (desk.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return deskMapper.toDTO(desk.get());
    }

    /**
     * 根据ID将桌子设置为开放状态。
     *
     * @param id 要设置开放状态的桌子的ID
     * @return 设置开放状态后桌子的数据传输对象
     * @throws EntityNotFoundException 当指定ID的桌子不存在时抛出
     */
    public DeskDTO openDesk(Long id) {
        Optional<Desk> desk = deskRepository.findById(id);
        if (desk.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        desk.get().setOpen(true);
        Desk save = deskRepository.save(desk.get());
        return deskMapper.toDTO(save);
    }

    /**
     * 根据ID将桌子设置为关闭状态。
     *
     * @param id 要设置关闭状态的桌子的ID
     * @return 设置关闭状态后桌子的数据传输对象
     * @throws EntityNotFoundException 当指定ID的桌子不存在时抛出
     */
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
