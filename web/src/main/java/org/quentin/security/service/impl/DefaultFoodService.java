package org.quentin.security.service.impl;

import org.quentin.security.dto.Food;
import org.quentin.security.mapper.FoodMapper;
import org.quentin.security.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultFoodService implements FoodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCatService.class);
    private final FoodMapper foodMapper;

    public DefaultFoodService(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }

    @Override
    public List<Food> getAllFoods() {
        return foodMapper.selectList(null);
    }

    @Override
    public List<Food> getFoodByCategory(String category) {
        return foodMapper.foodsByCategory(category);
    }
}
