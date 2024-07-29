package regiee_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.DishFlavor;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}