package com.ego.search;

/*
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

*/
/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/14
 * @Description: com.ego.search
 * @version: 1.0
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MyTest {

    @Autowired
    private SolrTemplate solr;

    @Value("${search.solr.collection}")
    private String collection;

    @Test
    public void testDecument(){
        SolrInputDocument sid = new SolrInputDocument();
        sid.setField("id","1");
        sid.setField("item_title","标题");
        sid.setField("item_sell_point","买点");
        sid.setField("item_price",123);
        sid.setField("item_image","http");
        sid.setField("item_category_name","类目名称");
        sid.setField("item_desc","描述");
        UpdateResponse ur = solr.saveDocument(collection, sid);
        if(ur.getStatus() == 0){
            solr.commit(collection);
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }


    }

}*/
