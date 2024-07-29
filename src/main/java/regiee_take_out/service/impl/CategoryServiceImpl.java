package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import regiee_take_out.common.CustomException;
import regiee_take_out.entity.Category;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.Employee;
import regiee_take_out.entity.Setmeal;
import regiee_take_out.mapper.CategoryMapper;
import regiee_take_out.mapper.EmployeeMapper;
import regiee_take_out.service.CategoryService;
import regiee_take_out.service.DishService;
import regiee_take_out.service.EmployeeService;
import regiee_take_out.service.SetMealService;

//实现类
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    /**
     * 根据id删除分类，删除之前需要进行判断
     *
     * @param id
     */
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;

    @Override
    public void remove(Long id) {
        log.info("id",id);
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
//是否关联菜品
        if (count > 0) {
            //抛出异常
            throw new CustomException("关联菜品不能删除");
        }
        //是否关联套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setMealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            throw new CustomException("关联套餐不能删除");
        }
//        正常删除分类
        super.removeById(id);
    }
}
