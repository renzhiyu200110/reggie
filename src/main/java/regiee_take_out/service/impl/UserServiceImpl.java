package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import regiee_take_out.dto.DishDto;
import regiee_take_out.entity.Dish;
import regiee_take_out.entity.DishFlavor;
import regiee_take_out.entity.User;
import regiee_take_out.mapper.DishMapper;
import regiee_take_out.mapper.UserMapper;
import regiee_take_out.service.DishFlavorService;
import regiee_take_out.service.DishService;
import regiee_take_out.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

//实现类
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Value("${spring.mail.username}")
    private String from;   // 邮件发送人

//    @Autowired
//    private JavaMailSender mailSender;


    /*public void sendMsg(String to, String subject, String context) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(context);

        // 真正的发送邮件操作，从 from到 to
        mailSender.send(mailMessage);
    }*/

}
