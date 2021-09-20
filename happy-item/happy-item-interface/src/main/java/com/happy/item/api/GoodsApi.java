package com.happy.item.api;




import com.happy.common.ov.PageResult;
import com.happy.item.bo.SpuBo;
import com.happy.item.pojo.Sku;
import com.happy.item.pojo.Spu;
import com.happy.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsApi {
    /**
     * 根据条件查询商品分页信息
     *
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/spu/page")
    public PageResult<SpuBo> querySpuBoByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );



    /**
     * 根据商品id查询商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable Long id);

    /**
     * 根据商品id查询sku
     *
     * @return
     */
    @GetMapping("/sku/list")
    public List<Sku> querySkuBySpuId(@RequestParam Long id);


    /**
     * 根据商品id查询商品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    public Spu querySpuById(@PathVariable("id") Long id);

    /**
     * 根据sku的id查询sku
     *
     * @param id
     * @return
     */
    @GetMapping("sku/{id}")
    public Sku querySkuById(@PathVariable("id") Long id);
}

