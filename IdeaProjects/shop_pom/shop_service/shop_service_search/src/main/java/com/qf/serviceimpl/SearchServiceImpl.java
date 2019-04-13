package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public List<Goods> searchGoods(String keyWord) {
        System.out.println("搜索服务获得着关键字： "+ keyWord);
        //创建SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        if (keyWord == null){
            solrQuery.setQuery("*:*");//查询所有
        }else {//搜索具体的关键字
            solrQuery.setQuery("gname:" + keyWord + " || ginfo" + keyWord);
        }

        List<Goods> list = new ArrayList<>();
        try {
            QueryResponse query = solrClient.query(solrQuery);
            SolrDocumentList results = query.getResults();
            for(SolrDocument document:results){
                Goods goods = new Goods();
                goods.setId(Integer.parseInt(document.get("id")+""));
                goods.setGname(document.get("gname")+"");
                goods.setGprice(BigDecimal.valueOf(Double.parseDouble(document.get("gprice")+"")));
                goods.setGimage(document.get("gimage")+"");
                goods.setGsave(Integer.parseInt(document.get("gsave")+""));
                list.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list);
        return list;
    }

    @Override
    public int addGoods(Goods goods) {
        //将商品添加到索引库中
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
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
