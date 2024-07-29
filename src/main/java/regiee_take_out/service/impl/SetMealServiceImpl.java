package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import regiee_take_out.common.CustomException;
import regiee_take_out.dto.SetmealDto;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.Setmeal;
import regiee_take_out.entity.SetmealDish;
import regiee_take_out.mapper.DishMapper;
import regiee_take_out.mapper.SetMealMapper;
import regiee_take_out.service.DishService;
import regiee_take_out.service.SetMealDishService;
import regiee_take_out.service.SetMealService;

import java.util.List;
import java.util.stream.Collectors;

//实现类
@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
    @Autowired
    private SetMealDishService setMealDishService;
    /**
     * 新增套餐需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
//保存套餐基本信息 setmeal里面执行insert
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

//        保存套餐和菜品和关联信息，操作setmeal，执行insert
        setMealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，还要删除和套餐关联的菜品数据
     * @param ids
     */
    @Transactional
    public void deleteWithDish(List<Long> ids) {
//        先查询套餐状态，看状态是不是售卖中
        LambdaQueryWrapper<Setmeal>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
       int count= this.count(queryWrapper);
       if(count>0){
           throw new CustomException("套餐在售卖");
       }
//       先删除套餐数据，setmeal
        this.removeByIds(ids);
//       删除关系表 setmeal -dish
       LambdaQueryWrapper<SetmealDish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
       lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
       setMealDishService.remove(lambdaQueryWrapper);


    }
}