package org.quentin.security.service;

import org.quentin.security.dto.Food;

import java.util.List;

public interface FoodService {
    List<Food> getAllFoods();

    List<Food> getFoodByCategory(String category);
}
