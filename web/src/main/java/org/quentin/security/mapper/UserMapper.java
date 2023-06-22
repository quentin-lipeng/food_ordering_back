package org.quentin.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.quentin.security.domain.dto.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT U.user_id, U.username, U.password, UA.role FROM user_info UI left join user U on UI.user_id = U.user_id left join user_authz UA on U.user_id = UA.user_id WHERE email = #{email}")
    User getUserByUserInfoEmail(String email);

}
