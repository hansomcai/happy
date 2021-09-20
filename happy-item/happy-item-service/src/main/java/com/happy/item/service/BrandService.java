package com.happy.item.service;

import com.happy.common.ov.PageResult;
import com.happy.item.pojo.Brand;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：
 * @date ：2021/8/24 14:49
 */
public interface BrandService {
    /**
     * 根据条件查询品牌分页信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    public void saveBrand(Brand brand, List<Long> cids);

    List<Brand> queryBrandByCategory(Long cid);
}
