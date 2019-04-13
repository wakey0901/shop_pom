package com.qf.service;

import com.qf.entity.Goods;
import java.util.List;

public interface ISearchService {
    //根据关键字搜索商品
    List<Goods> searchGoods(String KeyWord);
    //新增商品同步到搜索库
    int addGoods(Goods goods);
}
