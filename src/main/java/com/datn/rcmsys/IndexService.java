package com.datn.rcmsys;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    public void indexProduct(String index, String id, Map<String, Object> product) {
    	
        IndexRequest request = new IndexRequest(index)
                .id(id)
                .source(product);

        try {
            elasticsearchClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
