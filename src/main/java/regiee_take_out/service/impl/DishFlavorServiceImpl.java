package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.DishFlavor;
import regiee_take_out.mapper.DishFlavorMapper;
import regiee_take_out.mapper.DishMapper;
import regiee_take_out.service.DishFlavorService;
import regiee_take_out.service.DishService;

//实现类
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>implements DishFlavorService {

}
