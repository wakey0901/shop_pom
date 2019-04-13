package com.qf.shop_search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopSearchApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void contextLoads() {
    }


    @Test
    public void add(){
        //创建SolrInputDocument对象
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id",4);
        solrInputDocument.addField("gname","创维电视");
        solrInputDocument.addField("gimage","www.baidu.com");
        solrInputDocument.addField("ginfo","好电视，创维电视");
        solrInputDocument.addField("gprice",1899.99);
        solrInputDocument.addField("gsave","123");

        try {
            solrClient.add(solrInputDocument);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
