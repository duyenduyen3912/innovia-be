package com.datn.rcmsys;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    public List<String> suggestProducts(String indexName, String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // Xây dựng truy vấn tìm kiếm
        searchSourceBuilder.query(QueryBuilders.matchQuery("productName", query));
        searchRequest.source(searchSourceBuilder);

        // Thực hiện truy vấn
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

        // Xử lý kết quả
        return processSearchResults(searchResponse);
    }

    private List<String> processSearchResults(SearchResponse searchResponse) {
        List<String> productNames = new ArrayList<>();

        // Lặp qua các kết quả tìm kiếm
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            // Lấy map chứa thông tin của document
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            
            // Kiểm tra xem field "productName" có tồn tại không
            if (sourceAsMap.containsKey("productName")) {
                // Lấy giá trị của field "productName" và thêm vào danh sách
                String productName = (String) sourceAsMap.get("productName");
                productNames.add(productName);
            }
        }

        return productNames;
    }
}

