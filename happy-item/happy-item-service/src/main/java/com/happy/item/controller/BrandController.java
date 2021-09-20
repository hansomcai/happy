package com.happy.item.controller;

import com.happy.common.ov.PageResult;
import com.happy.item.pojo.Brand;
import com.happy.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/8/24 15:01
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "desc", required = false) Boolean desc
    ) {
        PageResult<Brand> brandPageResult = brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        return ResponseEntity.ok(brandPageResult);
    }

    @PostMapping
    public ResponseEntity saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.saveBrand(brand,cids);
        return ResponseEntity.created(null).build();
    }

    /**
     * 根据分类查询品牌
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategory(@PathVariable("cid") Long cid) {
        List<Brand> list = this.brandService.queryBrandByCategory(cid);
        if(list == null){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}
