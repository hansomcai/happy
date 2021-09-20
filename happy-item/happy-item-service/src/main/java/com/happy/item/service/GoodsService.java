package com.happy.item.service;

import com.happy.common.ov.PageResult;
import com.happy.item.bo.SpuBo;
import com.happy.item.pojo.Sku;
import com.happy.item.pojo.Spu;
import com.happy.item.pojo.SpuDetail;

import java.util.List;

public interface GoodsService {
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);


    void saveGoods(SpuBo spuBo);

    SpuDetail querySpuDetailBySpuId(Long id);

    List<Sku> qureySkuById(Long id);

    void updateGoods(SpuBo spuBo);
    public Spu querySpuById(Long id);
}
