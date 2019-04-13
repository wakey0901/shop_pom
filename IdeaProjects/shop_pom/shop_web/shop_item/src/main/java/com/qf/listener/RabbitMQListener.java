package com.qf.listener;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RabbitMQListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "goods_queue2")
    public void Msg(Goods goods){
        System.out.println("页面静态化工程接收到的MQ消息： "+goods);

        //生成静态页面
        String gimage = goods.getGimage();
        String[] images = gimage.split("\\|");

        try {
            Template template =  configuration.getTemplate("goodsitem.ftl");
            Map<String,Object> map = new HashMap<>();
            map.put("goods",goods);
            map.put("images",images);

            String path = this.getClass().getResource("/static/page/").getPath()+goods.getId()+".html";
            template.process(map,new FileWriter(path));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
