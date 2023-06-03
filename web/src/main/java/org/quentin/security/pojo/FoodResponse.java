package org.quentin.security.pojo;

import org.quentin.security.dto.Food;
import org.quentin.security.dto.FoodCategory;

import java.util.List;

public record FoodResponse(FoodCategory category, List<Food> foods) {
}
