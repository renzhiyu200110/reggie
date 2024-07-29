package regiee_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import regiee_take_out.dto.DishDto;
import regiee_take_out.entity.Category;
import regiee_take_out.entity.Dish;

public interface DishService extends IService<Dish> {
//新增菜品同时插入对应的口味数据,操作两个表dish dishflavor
    public void  saveWithFlavor(DishDto dishDto);
//    根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);
//更新菜品细心和口味信息
   public void updateWithFlavor(DishDto dishDto);
}
