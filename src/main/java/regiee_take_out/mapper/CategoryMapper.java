package regiee_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import regiee_take_out.entity.Category;
import regiee_take_out.entity.Employee;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
