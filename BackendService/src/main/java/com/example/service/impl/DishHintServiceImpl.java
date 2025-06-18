package com.example.service.impl;


import com.example.dto.DishHintDTO;
import com.example.dto.DishHintMapper;
import com.example.model.dish.Dish;
import com.example.model.dish.DishHint;
import com.example.repository.DishHintRepository;
import com.example.repository.DishRepository;
import com.example.service.DishHintService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DishHintServiceImpl implements DishHintService {

    @Autowired
    private DishHintRepository dishHintRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishHintMapper dishHintMapper;

    /**
     * 根据菜品提示 ID 获取对应的菜品提示信息。
     *
     * @param id 菜品提示的 ID，用于从数据库中查询特定的菜品提示。
     * @return 与传入 ID 对应的菜品提示数据传输对象（DTO）。
     * @throws EntityNotFoundException 当传入的 ID 在数据库中不存在对应的菜品提示时抛出此异常。
     */
    public DishHintDTO getDishHintById(Long id) {
        Optional<DishHint> dishHint = dishHintRepository.findById(id);
        if (dishHint.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return dishHintMapper.toDTO(dishHint.get());
    }

    /**
     * 创建新的菜品提示。
     * 此方法会将传入的菜品提示 DTO 对象的 ID 置为 null，
     * 以确保创建新的记录，然后调用辅助方法处理并保存新的菜品提示。
     *
     * @param dishHintDTO 包含新菜品提示信息的数据传输对象
     * @return 保存到数据库后的新菜品提示的数据传输对象
     */
    public DishHintDTO createDishHint(DishHintDTO dishHintDTO) {
        dishHintDTO.setId(null);
        return getDishHintDTO(dishHintDTO);
    }

    /**
     * 根据给定的 ID 更新菜品提示信息。
     *
     * @param id 要更新的菜品提示的 ID，从 URL 中获取。
     * @param dishHintDTO 包含更新信息的菜品提示数据传输对象。
     * @return 更新后的菜品提示数据传输对象。
     * @throws IllegalArgumentException 当 URL 中的 ID 与 DTO 中的 ID 不一致时抛出。
     */
    public DishHintDTO updateDishHint(Long id, DishHintDTO dishHintDTO) {
        if (!id.equals(dishHintDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }

        return getDishHintDTO(dishHintDTO);
    }

    /**
     * 根据菜品提示 ID 删除对应的菜品提示信息。
     *
     * @param id 要删除的菜品提示的 ID
     * @return 如果指定 ID 的菜品提示存在并成功删除，返回 true；若不存在，则返回 false
     */
    public boolean deleteDishHint(Long id) {
        if (dishHintRepository.existsById(id)) {
            dishHintRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 辅助方法，用于处理菜品提示 DTO 对象并保存到数据库。
     * 此方法会先根据 DTO 对象中的菜品 ID 查找对应的菜品实体，
     * 若找到则将菜品实体关联到菜品提示实体，最后保存并返回转换后的 DTO 对象。
     *
     * @param dishHintDTO 包含菜品提示信息的数据传输对象
     * @return 保存到数据库后的菜品提示的数据传输对象
     * @throws EntityNotFoundException 当 DTO 对象中的菜品 ID 在数据库中不存在对应的菜品时抛出此异常。
     */
    private DishHintDTO getDishHintDTO(DishHintDTO dishHintDTO) {
        Optional<Dish> dish = dishRepository.findById(dishHintDTO.getDishId());
        if (dish.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        DishHint dishHint = dishHintMapper.toEntity(dishHintDTO);
        dishHint.setDish(dish.get());
        DishHint save = dishHintRepository.save(dishHint);
        return dishHintMapper.toDTO(save);
    }
}
