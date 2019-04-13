package com.qf.listener;

import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Autowired
    private SolrClient solrClient;

    //监听指定的队列
    @RabbitListener(queues = "goods_queue1")
    public void Msg(Goods goods){
        //接收MQ消息
        System.out.println("搜索服务接收到MQ消息： "+goods);

        //同步商品信息到搜索索引库
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",goods.getId());
        document.addField("gname",goods.getGname());
        document.addField("ginfo",goods.getGinfo());
        document.addField("gsave",goods.getGsave());
        document.addField("gprice",goods.getGprice().doubleValue());
        document.addField("gimage",goods.getGimage());

        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
