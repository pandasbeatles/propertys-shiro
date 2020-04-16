package com.xiaochen.beatles.mapper;

import com.xiaochen.beatles.pojo.Product;

import java.util.List;


public interface ProductMapper {

    Product getProductid(int id);

    List<Product> getProduct();

}
