package org.quentin.security.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;

public class FoodCategory implements Serializable {

    @Serial
    private static final long serialVersionUID = 8223162260369464307L;
    @TableId(type = IdType.AUTO)
    private Integer catId;
    private String catName;
    private String catDesp;
    private String catLabel;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesp() {
        return catDesp;
    }

    public void setCatDesp(String catDesp) {
        this.catDesp = catDesp;
    }

    public String getCatLabel() {
        return catLabel;
    }

    public void setCatLabel(String catLabel) {
        this.catLabel = catLabel;
    }

    @Override
    public String toString() {
        return "FoodCategory{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                ", catDesp='" + catDesp + '\'' +
                ", catLabel='" + catLabel + '\'' +
                '}';
    }
}
