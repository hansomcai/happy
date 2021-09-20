package com.happy.item.api;


import com.happy.common.ov.PageResult;
import com.happy.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("brand")
public interface BrandApi {
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
    @GetMapping("/page")
    public PageResult<Brand> queryBrandsByPage
    (@RequestParam(name = "key", required = false) String key,
     @RequestParam(name = "page", defaultValue = "1") Integer page,
     @RequestParam(name = "rows", defaultValue = "5") Integer rows,
     @RequestParam(name = "sortBy", required = false) String sortBy,
     @RequestParam(name = "desc", required = false) Boolean desc
    );

    /**
     * 添加品牌
     *
     * @param brand
     * @param cids
     */
    @PostMapping
    public ResponseEntity saveBrand(Brand brand, @RequestParam("cids") List<Long> cids);

    /**
     * 根据商品分类id查询品牌
     *
     * @return
     */
    @GetMapping("/cid/{cid}")
    public List<Brand> queryBrandsByCid(@PathVariable Long cid);

    /**
     * 根据id查询品牌
     *
     * @return
     */
    @GetMapping("/{id}")
    public Brand queryBrandById(@PathVariable Long id);

}