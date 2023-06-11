package org.quentin.security.service;

import org.quentin.security.domain.dto.Food;
import org.quentin.security.domain.vo.FoodWithCat;

import java.util.List;

public interface FoodService {
    List<Food> getAllFoods();

    List<Food> getFoodByCategoryName(String categoryName);

    FoodWithCat getFoodByCategoryLabel(String categoryLabel);
}
