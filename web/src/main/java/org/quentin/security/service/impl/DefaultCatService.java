package org.quentin.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quentin.security.dto.FoodCategory;
import org.quentin.security.mapper.CatMapper;
import org.quentin.security.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCatService implements CatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCatService.class);
    private final CatMapper catMapper;

    public DefaultCatService(CatMapper catMapper) {
        this.catMapper = catMapper;
    }

    @Override
    public List<FoodCategory> getAllCats() {
        return catMapper.selectList(null);
    }

    @Override
    public FoodCategory getCatByLabel(String catLabel) {
        QueryWrapper<FoodCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("cat_label", catLabel);
        return catMapper.selectOne(wrapper);
    }
}
