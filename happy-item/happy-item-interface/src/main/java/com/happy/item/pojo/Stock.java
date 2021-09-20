package com.happy.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/1 15:38
 */
@Data
@Table(name="tb_stock")
public class Stock {
    @Id
    private Long skuId;

    /**
     * 秒杀可用库存
     */
    private Integer seckillStock;

    /**
     * 已秒杀数量
     */
    private Integer seckillTotal;

    /**
     * 正常库存
     */
    private Integer stock;
}
