package regiee_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import regiee_take_out.common.R;
import regiee_take_out.entity.User;
import regiee_take_out.service.UserService;
import regiee_take_out.utils.SMSUtils;
import regiee_take_out.utils.ValidateCodeUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 发送邮箱验证码
     *
     * @param user
     * @return
     */
    /*@PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取邮箱账号
        String phone = user.getPhone();

        String subject = "瑞吉餐购登录验证码";

        if (!StringUtils.isEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            String context = "欢迎使用瑞吉餐购，登录验证码为: " + code + ",五分钟内有效，请妥善保管!";
            log.info("code={}", code);

            // 真正地发送邮箱验证码
            userService.sendMsg(phone, subject, context);

            //  将随机生成的验证码保存到session中
            //            session.setAttribute(phone, code);

            // 验证码由保存到session 优化为 缓存到Redis中，并且设置验证码的有效时间为 5分钟
            redisTemplate.opsForValue().set(phone, code, 51, TimeUnit.MINUTES);

            return R.success("验证码发送成功，请及时查看!");
        }
        return R.error("验证码发送失败，请重新输入!");
    }*/

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> senMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(!StringUtils.isEmpty(phone)){
            //生成验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码code:{}",code);

            //调用阿里云提供的api发送短信
            //SMSUtils.sendMessage("阿里云短信测试","SMS_153123124",phone,code);

            //需要将生成的验证码保存到Session
//            session.setAttribute(phone,code);

            //将生成的验证码缓存到redis中，并且设置有效期为5分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);


            return R.success("手机验证码短信发送成功");
        }

        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @Param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从Session中获取保存的验证码
//        Object codeInSession = session.getAttribute(phone);

        //从Redis中获取缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //如果用户登录成功，删除Redis中的缓存验证码
            redisTemplate.delete(phone);

            return R.success(user);
        }
        return R.error("登录失败");
    }

}
