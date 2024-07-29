package regiee_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import regiee_take_out.dto.DishDto;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.User;

public interface UserService extends IService<User> {
    /**
     * 发送邮箱
     * @param to
     * @param subject
     * @param context
     */
//    void sendMsg(String to,String subject,String context);


}
