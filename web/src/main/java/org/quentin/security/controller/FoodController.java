package org.quentin.security.controller;

import org.quentin.security.common.CommonResponse;
import org.quentin.security.dto.Food;
import org.quentin.security.dto.FoodCategory;
import org.quentin.security.pojo.FoodResponse;
import org.quentin.security.service.CatService;
import org.quentin.security.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/food")
public class FoodController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodController.class);

    private final CatService catService;
    private final FoodService foodService;

    public FoodController(CatService catService, FoodService foodService) {
        this.catService = catService;
        this.foodService = foodService;
    }

    @GetMapping("/categories")
    public ResponseEntity<CommonResponse<List<FoodCategory>>> catList() {
        List<FoodCategory> categories = catService.getAllCats();
        CommonResponse<List<FoodCategory>> commonResponse = new CommonResponse<>("categories", categories);
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * @param cat category's label
     * @return todo add javadoc
     */
    @GetMapping("/category/{cat}")
    public ResponseEntity<CommonResponse<FoodResponse>> getByCat(@PathVariable String cat) {
        if ("all".equals(cat)) {
            FoodCategory category = new FoodCategory();
            category.setCatName("all");
            List<Food> foods = foodService.getAllFoods();
            CommonResponse<FoodResponse> commonResponse =
                    new CommonResponse<>("foods", new FoodResponse(category, foods));
            return ResponseEntity.ok(commonResponse);
        }

        FoodCategory existCat = catService.getCatByLabel(cat);
        if (Objects.isNull(existCat)) {
            CommonResponse<FoodResponse> commonResponse =
                    new CommonResponse<>("none",
                            new FoodResponse(new FoodCategory(), Collections.emptyList()));
            return ResponseEntity.badRequest().body(commonResponse);
        }

        List<Food> byCategory = foodService.getFoodByCategory(existCat.getCatLabel());
        CommonResponse<FoodResponse> commonResponse =
                new CommonResponse<>("by cate", new FoodResponse(existCat, byCategory));
        return ResponseEntity.ok(commonResponse);
    }

}
