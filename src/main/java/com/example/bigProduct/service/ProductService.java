package com.example.bigProduct.service;

import com.example.bigProduct.domain.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    List<Product> findAll();
}
