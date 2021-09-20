package com.happy.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.happy.common.ov.PageResult;
import com.happy.item.bo.SpuBo;
import com.happy.item.mapper.*;
import com.happy.item.pojo.*;
import com.happy.item.service.CategoryService;
import com.happy.item.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceImpl.class);
    @Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(saleable!=null){
            criteria.orEqualTo("saleable",saleable);
        }
        example.setOrderByClause("last_update_time DESC");
        List<Spu> spus = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo(spus);
        List<SpuBo> spuBos = spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu,spuBo);
            List<String> names = categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names,"/"));
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            return spuBo;
        }).collect(Collectors.toList());
        return new PageResult(pageInfo.getTotal(),spuBos);

    }

    @Override
    public void saveGoods(SpuBo spuBo) {
        //保存商品spu
        //保证id为自增长，防止页面恶意提交id参数
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(false);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insertSelective(spuBo);

        // 保存spu详情
        spuBo.getSpuDetail().setSpuId(spuBo.getId());
        this.spuDetailMapper.insert(spuBo.getSpuDetail());
        List<Sku> skus = spuBo.getSkus();
        Long spuId = spuBo.getId();
        List<Stock> stocks=new ArrayList<Stock>();
        // 保存sku和库存信息
        for (Sku sku : skus) {
            if (!sku.getEnable()) {
                continue;
            }
            // 保存sku
            sku.setSpuId(spuId);
            // 默认不参与任何促销
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insert(sku);

            // 保存库存信息
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        }
        stockMapper.insertList(stocks);
    }

    @Override
    public SpuDetail querySpuDetailBySpuId(Long id) {
        return spuDetailMapper.selectByPrimaryKey(id);

    }

    @Override
    public List<Sku> qureySkuById(Long id) {
        Sku skuObject = new Sku();
        skuObject.setId(id);
        List<Sku> skus = skuMapper.select(skuObject);
        skus.forEach(sku ->{
            Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());

        });
        return skus;
    }

    @Override
    public void updateGoods(SpuBo spuBo) {
        //先删除原来sku和库存
        List<Sku> oldSkus =this.qureySkuById(spuBo.getId());
        if (!CollectionUtils.isEmpty(oldSkus)){
            //获取所有以前的sku的id
            oldSkus.forEach(oldSku -> {
                stockMapper.deleteByPrimaryKey(oldSku.getId());
            });
            //删除以前的sku
            Sku sku = new Sku();
            sku.setSpuId(spuBo.getId());
            skuMapper.delete(sku);
        }
        //调用保存sku和库存的方法
        this.saveSkuAndStock(spuBo);
        //更新商品spu
        spuBo.setLastUpdateTime(new Date());
        spuBo.setCreateTime(null);
        spuBo.setValid(null);
        spuBo.setSaleable(null);
        spuMapper.updateByPrimaryKeySelective(spuBo);
        //更新商品详情spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);
        sendMessage("update",spuBo.getId());
    }

    @Override
    public Spu querySpuById(Long id) {
        return this.spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存sku和库存
     *
     * @param spuBo
     */
    private void saveSkuAndStock(SpuBo spuBo) {
        List<Sku> skus = spuBo.getSkus();
        skus.forEach(sku ->{
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(spuBo.getLastUpdateTime());
            sku.setLastUpdateTime(spuBo.getLastUpdateTime());
            skuMapper.insertSelective(sku);

            //保存库存Stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insertSelective(stock);
        });
    }

    public void sendMessage(String type,Long id){
        try {
            amqpTemplate.convertAndSend("item." + type, id);
        } catch (AmqpException e) {
            LOGGER.error("发送消息失败，消息类型：{}，商品id：{}", "item." + type, id);
        }
    }
}
