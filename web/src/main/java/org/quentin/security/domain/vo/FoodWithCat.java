package org.quentin.security.domain.vo;

import org.quentin.security.domain.dto.Food;

import java.util.List;

public class FoodWithCat {
    private String catLabel;
    private List<Food> foods;

    public FoodWithCat() {
    }

    public FoodWithCat(String catLabel, List<Food> foods) {
        this.catLabel = catLabel;
        this.foods = foods;
    }

    public String getCatLabel() {
        return catLabel;
    }

    public void setCatLabel(String catLabel) {
        this.catLabel = catLabel;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "FoodWithCat{" +
                "catLabel='" + catLabel + '\'' +
                ", foods=" + foods +
                '}';
    }
}
