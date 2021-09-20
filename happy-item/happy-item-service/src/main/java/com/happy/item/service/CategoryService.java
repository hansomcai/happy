package com.happy.item.service;

import com.happy.item.pojo.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 根据parentId查询子类目
     *
     * @param pid
     * @return
     */
    List<Category> queryCategoriesByPid(Long pid);

    /**
     * 根据id查询分类的名称(集合)
     *
     * @param ids
     * @return
     */
    List<String> queryNamesByIds(List<Long> ids);
}
