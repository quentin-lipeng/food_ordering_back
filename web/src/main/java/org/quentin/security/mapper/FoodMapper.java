package org.quentin.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.quentin.security.domain.dto.Food;
import org.quentin.security.domain.vo.FoodWithCat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodMapper extends BaseMapper<Food> {

    List<Food> foodsByCategoryName(String categoryName);

    FoodWithCat foodsByCateLabel(String catLabel);
}
