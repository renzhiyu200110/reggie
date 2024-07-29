package regiee_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import regiee_take_out.common.R;
import regiee_take_out.entity.Employee;
import regiee_take_out.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.*;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //    1员工登录
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
//        1页面提交密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        2根据页面提交用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(queryWrapper);
//3是否查到
        if (one == null) {
            return R.error("登录失败");
        }
//        对比密码
        if (!one.getPassword().equals(password)) {
            return R.error("登录失败");
        }

//        查看员工状态是否可用
        if (one.getStatus() == 0) {
            return R.error("账号禁用");
        }
//        登陆成功
        httpServletRequest.getSession().setAttribute("employee", one.getId());
        return R.success(one);
//        return null;
    }

    //    2员工退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新员工新增信息：{}", employee.toString());
//        设置一个初始密码，进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
////    获得当前用户id
//        Long employee1 = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(employee1);
//        employee.setUpdateUser(employee1);


        employeeService.save((employee));
        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
//        log.info("page={},pagesize={},name={}",page,pageSize,name);
//        return null;
//构造分页构造器
        Page page1 = new Page(page, pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
////        添加统计
        queryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
////查询
        employeeService.page(page1, queryWrapper);
        return R.success(page1);

    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
//        Long employee1 = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(employee1);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return null;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没查询到对应员工xinxi");

    }
}
