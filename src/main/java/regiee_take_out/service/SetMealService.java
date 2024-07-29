package regiee_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import regiee_take_out.dto.SetmealDto;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {
    /**
     * 新增套餐需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，还要删除和套餐关联的菜品数据
     * @param ids
     */
    public void deleteWithDish(List<Long> ids);

}
