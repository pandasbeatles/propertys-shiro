package com.xiaochen.beatles.test;


import com.xiaochen.beatles.mapper.AdminMapper;
import com.xiaochen.beatles.mapper.ProductMapper;
import com.xiaochen.beatles.mapper.UserMapper;
import com.xiaochen.beatles.pojo.Admin;
import com.xiaochen.beatles.pojo.Product;
import com.xiaochen.beatles.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * @Author xiaochen
 * @Description
 * @Date 21:43 2020/3/26
 * @Param
 * @return
 **/


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AutowiredBean {
    @Autowired
    ProductMapper productMapper;

    @Test
    public void getbeans() {
        List<Product> product = productMapper.getProduct();
        System.out.println(product);
    }
}
