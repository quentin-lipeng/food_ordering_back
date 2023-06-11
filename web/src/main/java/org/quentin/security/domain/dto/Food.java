package org.quentin.security.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

public class Food implements Serializable {

    @Serial
    private static final long serialVersionUID = 2015271981804236380L;
    @TableId(type = IdType.AUTO)
    private Integer foodId;
    private String foodName;
    private String foodDesp;
    private Double foodPrice;
    private String foodImg;
    private Timestamp createTime;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDesp() {
        return foodDesp;
    }

    public void setFoodDesp(String foodDesp) {
        this.foodDesp = foodDesp;
    }

    public Double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(String foodImg) {
        this.foodImg = foodImg;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", foodDesp='" + foodDesp + '\'' +
                ", foodPrice=" + foodPrice +
                ", foodImg='" + foodImg + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
