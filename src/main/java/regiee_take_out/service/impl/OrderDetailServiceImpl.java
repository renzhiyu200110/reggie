package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import regiee_take_out.entity.OrderDetail;
import regiee_take_out.mapper.OrderDetailMapper;
import regiee_take_out.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}