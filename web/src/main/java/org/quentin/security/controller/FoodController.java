package org.quentin.security.controller;

import org.quentin.security.domain.vo.CommonResponse;
import org.quentin.security.domain.dto.Food;
import org.quentin.security.domain.dto.FoodCategory;
import org.quentin.security.service.CatService;
import org.quentin.security.service.FoodService;
import org.quentin.security.domain.vo.FoodWithCat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/category/{cat}")
    public ResponseEntity<CommonResponse<FoodWithCat>> getByCatDemo(@PathVariable String cat) {

        FoodWithCat foodWithCat;
        if ("all".equals(cat)) {
            List<Food> allFoods = foodService.getAllFoods();
            foodWithCat = new FoodWithCat("all", allFoods);
        } else {
            foodWithCat = foodService.getFoodByCategoryLabel(cat);
        }
        CommonResponse<FoodWithCat> allFoodsResponse = new CommonResponse<>("all foods", foodWithCat);
        return ResponseEntity.ok(allFoodsResponse);
    }

}
