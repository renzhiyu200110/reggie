package regiee_take_out.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import regiee_take_out.entity.AddressBook;
import regiee_take_out.entity.User;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
