package org.quentin.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.quentin.security.domain.dto.Token;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenMapper extends BaseMapper<Token> {
}
