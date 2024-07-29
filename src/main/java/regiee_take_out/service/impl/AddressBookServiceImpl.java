package regiee_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import regiee_take_out.entity.AddressBook;
import regiee_take_out.entity.User;
import regiee_take_out.mapper.AddressBookMapper;
import regiee_take_out.mapper.UserMapper;
import regiee_take_out.service.AddressBookService;
import regiee_take_out.service.UserService;

//实现类
@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
