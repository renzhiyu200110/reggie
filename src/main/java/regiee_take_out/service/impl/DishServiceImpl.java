package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import regiee_take_out.dto.DishDto;
import regiee_take_out.entity.Category;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.DishFlavor;
import regiee_take_out.mapper.CategoryMapper;
import regiee_take_out.mapper.DishMapper;
import regiee_take_out.service.CategoryService;
import regiee_take_out.service.DishFlavorService;
import regiee_take_out.service.DishService;

import java.awt.datatransfer.DataFlavor;
import java.util.List;
import java.util.stream.Collectors;

//实现类
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品再保存口味
     *
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
//        把基本信息保存到dish
        this.save(dishDto);
        Long id = dishDto.getId();//菜品id
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId((id));
            return item;
        }).collect(Collectors.toList());
//把口味保存到dishflavor
        dishFlavorService.saveBatch(flavors);
    }

    public DishDto getByIdWithFlavor(Long id) {
//        先查询基本信息
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

//        在查询口味信息,从dishfalvour查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
//更新dish表信息
        this.updateById(dishDto);
//        清理当前蔡品对应口味数据，dish-flavor表delete
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
//                添加提交的口味数据-insert
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
//把口味保存到dishflavor
        dishFlavorService.saveBatch(flavors);

    }

}
