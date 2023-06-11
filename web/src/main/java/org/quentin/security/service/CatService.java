package org.quentin.security.service;

import org.quentin.security.domain.dto.FoodCategory;

import java.util.List;

public interface CatService {
    List<FoodCategory> getAllCats();

    FoodCategory getCatByLabel(String catLabel);
}
