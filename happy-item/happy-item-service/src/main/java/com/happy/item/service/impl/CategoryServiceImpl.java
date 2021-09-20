package com.happy.item.service.impl;

import com.happy.item.mapper.CategoryMapper;
import com.happy.item.pojo.Category;
import com.happy.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     *@Description
     *@Param  * @param pid
     *@Return java.util.List<com.happy.item.pojo.Category>
     *@Author shenjuncai
     *@Date 2021/8/23
     *@Time 15:56
     */
    public List<Category> queryCategoriesByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    /**
     * 根据id列表查询分类信息
     *
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> categories = categoryMapper.selectByIdList(ids);
        List<String> categoryNames = categories.stream().map(category -> category.getName()).collect(Collectors.toList());
        return categoryNames;
    }
}
