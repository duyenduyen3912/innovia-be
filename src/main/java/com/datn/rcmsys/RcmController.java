package com.datn.rcmsys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RcmController {

    @Autowired
    private IndexService productService;

    @Autowired
    private SearchService searchService;

    @GetMapping("/index-product")
    public String indexProduct() {
        Map<String, Object> product = new HashMap<>();
        product.put("productName", "Tủ mây đan trang trí phòng khách");
        product.put("description", "Tủ mây đan trang trí phòng khách");
        product.put("category", "Tủ");
        product.put("price", 250000);

        productService.indexProduct("products", "35", product);
        return "ok";
    }

    @GetMapping("/suggest-products")
    public List<String> suggestProducts(@RequestParam String query) throws IOException {
    	List<String> rcm = searchService.suggestProducts("products", query);
    	return rcm;
    }
}

