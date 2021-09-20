package com.happy.item.bo;

import com.happy.item.pojo.Sku;
import com.happy.item.pojo.Spu;
import com.happy.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/1 10:45
 */
@Data
public class SpuBo extends Spu {
    String cname;// 商品分类名称

    String bname;// 品牌名称
    /**
     * 商品详情
     */
    SpuDetail spuDetail;

    /**
     * sku列表
     */
    List<Sku> skus;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

}
