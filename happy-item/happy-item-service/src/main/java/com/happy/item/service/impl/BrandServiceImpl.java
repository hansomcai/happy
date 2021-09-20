package com.happy.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.happy.common.enums.ExceptionEnum;
import com.happy.common.exception.CommonException;
import com.happy.common.ov.PageResult;
import com.happy.item.mapper.BrandMapper;
import com.happy.item.pojo.Brand;
import com.happy.item.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/8/24 14:52
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        //执行查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)){
            throw new CommonException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //获取分页信息
        PageInfo pageInfo = new PageInfo(brands);
        PageResult<Brand> pageResult = new PageResult<Brand>(pageInfo.getTotal(), brands);
        return pageResult;

    }
    @Transactional
    public void saveBrand(final Brand brand, List<Long> cids) {
        int count=brandMapper.insertSelective(brand);
        if(count!=1){
            throw new CommonException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        int countc=0;
        for (Long cid:cids) {
            countc=brandMapper.insertCategoryAndBrand(cid,brand.getId());
            if(countc!=1){
                throw new CommonException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }

    @Override
    public List<Brand> queryBrandByCategory(Long cid) {
        return this.brandMapper.queryByCategoryId(cid);
    }
}
