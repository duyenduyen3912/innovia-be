package com.datn.rcmsys;

import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.http.Header;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

	@Bean
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

        // Thêm header vào yêu cầu
        builder.setDefaultHeaders(
                new Header[]{
                        new BasicHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=7"),
                        new BasicHeader("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7")
                }
        );

        return new RestHighLevelClient(builder);
    }
}
