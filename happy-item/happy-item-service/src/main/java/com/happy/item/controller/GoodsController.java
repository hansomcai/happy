package com.happy.item.controller;

import com.happy.common.ov.PageResult;
import com.happy.item.bo.SpuBo;
import com.happy.item.pojo.Sku;
import com.happy.item.pojo.Spu;
import com.happy.item.pojo.SpuDetail;
import com.happy.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/1 10:05
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ){
        PageResult<SpuBo> pageResult = goodsService.querySpuBoByPage(key, saleable, page, rows);
        if (pageResult == null || CollectionUtils.isEmpty(pageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);

    }

    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/spu/detail/{id}")
    public  ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable  Long id){
        SpuDetail spuDetail =goodsService.querySpuDetailBySpuId(id);
        if(spuDetail == null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(spuDetail);
    }

    /**
     * 根据商品id查询sku
     *
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam Long id){
        List<Sku> skus = goodsService.qureySkuById(id);
        if (CollectionUtils.isEmpty(skus)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        goodsService.updateGoods(spuBo);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuById(id);
        if(spu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spu);
    }
}
