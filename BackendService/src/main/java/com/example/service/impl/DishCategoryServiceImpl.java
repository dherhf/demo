package com.example.service.impl;

import com.example.dto.DishCategoryDTO;
import com.example.dto.DishCategoryMapper;
import com.example.model.dish.Dish;
import com.example.model.dish.DishCategory;
import com.example.repository.DishCategoryRepository;
import com.example.service.DishCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishCategoryServiceImpl implements DishCategoryService {

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Autowired
    private DishCategoryMapper dishCategoryMapper;

    /**
     * 获取所有菜品分类信息。
     * 该方法会调用 DishCategoryRepository 的 findAll 方法从数据库中获取所有的菜品分类实体，
     * 然后使用 Java Stream API 将每个实体通过 DishCategoryMapper 转换为对应的 DTO 对象，
     * 最后将这些 DTO 对象收集到一个列表中并返回。
     *
     * @return 包含所有菜品分类信息的 DishCategoryDTO 列表
     */
    public List<DishCategoryDTO> getAllDishCategory() {
        List<DishCategory> dishCategory = dishCategoryRepository.findAll();
        return dishCategory.stream().map(dishCategoryMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * 根据菜品分类 ID 获取对应的菜品分类信息。
     *
     * @param id 菜品分类的 ID，用于从数据库中查询特定的菜品分类。
     * @return 与传入 ID 对应的菜品分类数据传输对象（DTO）。
     * @throws EntityNotFoundException 当传入的 ID 在数据库中不存在对应的菜品分类时抛出此异常。
     */
    public DishCategoryDTO getDishCategoryById(Long id) {
        Optional<DishCategory> dishCategory = dishCategoryRepository.findById(id);
        if (dishCategory.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return dishCategoryMapper.toDTO(dishCategory.get());
    }

    /**
     * 创建新的菜品分类。
     * 此方法会将传入的菜品分类 DTO 对象的 ID 和菜品 ID 列表置为 null，
     * 以确保创建新的记录，然后将其转换为实体对象保存到数据库，
     * 最后将保存后的实体对象转换为 DTO 对象返回。
     *
     * @param dishCategoryDTO 包含新菜品分类信息的数据传输对象
     * @return 保存到数据库后的新菜品分类的数据传输对象
     */
    public DishCategoryDTO createDishCategory(DishCategoryDTO dishCategoryDTO) {
        dishCategoryDTO.setId(null);
        dishCategoryDTO.setDishIds(null);
        DishCategory save = dishCategoryRepository.save(dishCategoryMapper.toEntity(dishCategoryDTO));
        return dishCategoryMapper.toDTO(save);

    }

    /**
     * 根据给定的 ID 更新菜品分类信息。
     *
     * @param id 要更新的菜品分类的 ID，从 URL 中获取。
     * @param dishCategoryDTO 包含更新信息的菜品分类数据传输对象。
     * @return 更新后的菜品分类数据传输对象。
     * @throws IllegalArgumentException 当 URL 中的 ID 与 DTO 中的 ID 不一致时抛出。
     * @throws EntityNotFoundException 当指定 ID 的菜品分类在数据库中不存在时抛出。
     */
    public DishCategoryDTO updateDishCategory(Long id, DishCategoryDTO dishCategoryDTO) {
        // 1. 校验URL ID与DTO ID是否一致
        if (!id.equals(dishCategoryDTO.getId())) {
            throw new IllegalArgumentException("bad request");
        }

        // 2. 检查ID是否存在
        if (!dishCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("not found");
        }

        DishCategory save = dishCategoryRepository.save(dishCategoryMapper.toEntity(dishCategoryDTO));
        return dishCategoryMapper.toDTO(save);
    }

    /**
     * 根据菜品分类 ID 删除对应的菜品分类信息。
     *
     * @param id 要删除的菜品分类的 ID
     * @return 如果指定 ID 的菜品分类存在并成功删除，返回 true；若不存在，则返回 false
     */
    public boolean deleteDishCategory(Long id) {
        if (dishCategoryRepository.existsById(id)) {
            dishCategoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * 根据菜品分类 ID 获取该分类下的所有菜品。
     * 此方法会先尝试通过传入的菜品分类 ID 从数据库中查询对应的菜品分类实体。
     * 如果未找到对应的菜品分类，将抛出 EntityNotFoundException 异常。
     * 若找到对应的菜品分类，则返回该分类下的所有菜品列表。
     *
     * @param id 菜品分类的 ID，用于从数据库中查询特定的菜品分类。
     * @return 该菜品分类下的所有菜品列表。
     * @throws EntityNotFoundException 当传入的 ID 在数据库中不存在对应的菜品分类时抛出此异常。
     */
    public List<Dish> getDishByDishCategory(Long id) {
        Optional<DishCategory> dishCategory = dishCategoryRepository.findById(id);
        if (dishCategory.isEmpty()) {
            throw new EntityNotFoundException("not found");
        }
        return dishCategory.get().getDishes();
    }
}
