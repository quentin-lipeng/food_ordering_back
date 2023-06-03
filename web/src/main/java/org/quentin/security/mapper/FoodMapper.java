package org.quentin.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.quentin.security.dto.Food;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodMapper extends BaseMapper<Food> {

    List<Food> foodsByCategory(String category);
}
