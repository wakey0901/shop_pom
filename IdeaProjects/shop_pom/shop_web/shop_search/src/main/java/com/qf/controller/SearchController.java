package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @RequestMapping("/searchByKeyWord")
    public String searchByKeyWord(String keyWord, ModelMap map) {
        System.out.println("搜索关键字： "+ keyWord);
        List<Goods> goods = searchService.searchGoods(keyWord);
        System.out.println("调用搜索服务后，获取得到的结果： "+goods);
        map.put("goods",goods);
        return "searchlist";
    }

}
