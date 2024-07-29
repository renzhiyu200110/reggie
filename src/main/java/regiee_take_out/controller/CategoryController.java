package regiee_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import regiee_take_out.common.R;
import regiee_take_out.entity.Category;
import regiee_take_out.entity.Employee;
import regiee_take_out.service.CategoryService;
import regiee_take_out.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);

        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> pageR(Integer page, Integer pageSize) {
//        分页构造器，条件构造器
        Page<Category> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        根据sort排序，分页查询
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(page1, queryWrapper);
        return R.success(page1);
    }

    /**
     * 根据id删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除分类,id:::{}", ids);
        categoryService.remove(ids);
        return R.success("分类删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类:{}", category);
        categoryService.updateById(category);
        return R.success("修改分类成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>>list(Category category){
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
//        分别是条件和排序条件
        queryWrapper.eq(category.getType()!=null ,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
