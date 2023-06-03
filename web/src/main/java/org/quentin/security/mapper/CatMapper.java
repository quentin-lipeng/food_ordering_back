package org.quentin.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.quentin.security.dto.FoodCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface CatMapper extends BaseMapper<FoodCategory> {
}
