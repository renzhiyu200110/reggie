package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import regiee_take_out.entity.ShoppingCart;
import regiee_take_out.mapper.ShoppingCartMapper;
import regiee_take_out.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
