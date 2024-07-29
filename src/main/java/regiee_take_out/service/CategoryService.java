package regiee_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import regiee_take_out.entity.Category;
import regiee_take_out.entity.Employee;

public interface CategoryService extends IService<Category> {
    public  void remove(Long id);

}
