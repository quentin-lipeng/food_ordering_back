package org.quentin.security.service;

import org.quentin.security.dto.FoodCategory;

import java.util.List;

public interface CatService {
    List<FoodCategory> getAllCats();

    FoodCategory getCatByLabel(String catLabel);
}
