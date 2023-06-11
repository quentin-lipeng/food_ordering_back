package org.quentin.security.domain.vo;

import org.quentin.security.domain.dto.Food;
import org.quentin.security.domain.dto.FoodCategory;

import java.util.List;

public record FoodResponse(FoodCategory category, List<Food> foods) {
}
