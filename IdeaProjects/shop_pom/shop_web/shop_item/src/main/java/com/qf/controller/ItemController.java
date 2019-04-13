package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private Configuration configuration;

    /**
     * 生成静态页面
     * @param gid
     * @return
     */
    @RequestMapping("/createHtml")
    public String createHtml(int gid, HttpServletRequest request){
        //通过id查询商品信息
        Goods goods = goodsService.queryById(gid);
        //分隔图片
        String[] images = goods.getGimage().split("\\|");

        //通过模板生成商品详情页面
        try {
            Template template = configuration.getTemplate("goodsitem.ftl");
            //后台传递商品信息到前端静态页面
            Map<String,Object> map = new HashMap<>();
            map.put("goods",goods);
            map.put("images",images);
            //后台传递项目路径到前端页面
            map.put("context",request.getContextPath());

            //生成静态页面
            //指定静态页面的路径，以商品id作为页面的名字
            String path =  this.getClass().getResource("/static/page/").getPath()+goods.getId()+".html";
            System.out.println("静态页面路径： "+path);
            template.process(map,new FileWriter(path));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
