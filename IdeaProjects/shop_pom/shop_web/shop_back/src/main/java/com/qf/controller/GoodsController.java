package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;
    /**
     * 查询商品列表
     */
    @RequestMapping("/list")
    public String goodslist(ModelMap map){
        List<Goods> goods = goodsService.queryAll();
        map.put("goods",goods);
        return "goodslist";
    }

    /**
     * 新增商品
     */
    @RequestMapping("/insert")
    public String goodsinsert(Goods goods){
        int count = goodsService.insert(goods);
        return "redirect:/goods/list";
    }
}
