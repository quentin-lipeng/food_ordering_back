package org.quentin.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.quentin.security.domain.dto.UserAuthz;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthzMapper extends BaseMapper<UserAuthz> {
}
