package com.happy.web.service;

import com.happy.item.pojo.*;
import com.happy.web.client.BrandClient;
import com.happy.web.client.CategoryClient;
import com.happy.web.client.GoodsClient;
import com.happy.web.client.SpecificationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/2 11:19
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecificationClient specificationClient;

    private static final Logger logger = LoggerFactory.getLogger(GoodsService.class);

    public Map<String, Object> loadModel(Long spuId){

        try {
            // 查询spu
            Spu spu = this.goodsClient.querySpuById(spuId);

            // 查询spu详情
            SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spuId);

            // 查询sku
            List<Sku> skus = this.goodsClient.querySkuBySpuId(spuId);

            // 查询品牌
            Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

            // 查询分类
            List<Category> categories = getCategories(spu);

            // 查询组内参数
            List<SpecGroup> specGroups = this.specificationClient.querySpecsByCid(spu.getCid3());

            // 查询所有特有规格参数
            List<SpecParam> specParams = this.specificationClient.querySpecParams( null,spu.getCid3(), null,null);
            // 处理规格参数
            Map<Long, String> paramMap = new HashMap<>();
            specParams.forEach(param->{
                paramMap.put(param.getId(), param.getName());
            });

            Map<String, Object> map = new HashMap<>();
            map.put("spu", spu);
            map.put("spuDetail", spuDetail);
            map.put("skus", skus);
            map.put("brand", brand);
            map.put("categories", categories);
            map.put("groups", specGroups);
            map.put("params", paramMap);
            return map;
        } catch (Exception e) {
            logger.error("加载商品数据出错,spuId:{}", spuId, e);
        }
        return null;
    }

    private List<Category> getCategories(Spu spu) {
        try {
            List<String> names = this.categoryClient.queryNamesByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            Category c1 = new Category();
            c1.setName(names.get(0));
            c1.setId(spu.getCid1());

            Category c2 = new Category();
            c2.setName(names.get(1));
            c2.setId(spu.getCid2());

            Category c3 = new Category();
            c3.setName(names.get(2));
            c3.setId(spu.getCid3());

            return Arrays.asList(c1, c2, c3);
        } catch (Exception e) {
            logger.error("查询商品分类出错，spuId：{}", spu.getId(), e);
        }
        return null;
    }
}
