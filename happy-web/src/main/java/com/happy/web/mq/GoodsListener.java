package com.happy.web.mq;

import com.happy.web.service.GoodsHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/3 15:23
 */
@Component
public class GoodsListener {

    @Autowired
    private GoodsHtmlService goodsHtmlService;
    @RabbitListener(bindings = @QueueBinding(
            value =  @Queue(name="happy.item.create.queue",durable = "true"),
            exchange = @Exchange(name="happy.item.exchange",type = ExchangeTypes.TOPIC,durable = "true"),
            key = {"item.insert","item.update"}
    ))
    public void listenCreate(Long id){
        if (id == null) {
            return;
        }
        // 创建页面
        goodsHtmlService.createHtml(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value =  @Queue(name="happy.item.delete.queue",durable = "true"),
            exchange = @Exchange(name="happy.item.exchange",type = ExchangeTypes.TOPIC,durable = "true"),
            key = {"item.delete"}
    ))
    public void listenDelete(Long id){
        if (id == null) {
            return;
        }
        // 创建页面
        goodsHtmlService.deleteHtml(id);
    }

}
