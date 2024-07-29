package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import regiee_take_out.entity.Employee;
import regiee_take_out.mapper.EmployeeMapper;
import regiee_take_out.service.EmployeeService;
//实现类
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>implements EmployeeService {

}
