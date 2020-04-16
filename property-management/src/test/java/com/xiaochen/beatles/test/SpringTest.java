package com.xiaochen.beatles.test;

import com.xiaochen.beatles.pojo.Msg;
import com.xiaochen.beatles.service.MsgService;
import com.xiaochen.beatles.util.Utils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringTest {
    @Autowired
    MsgService msgService;

    @Test
    public void getbeans() {
        HashMap<Object, Object> mapPage = new HashMap<>();
        mapPage.put("msgClass", 0);
        mapPage.put("page", 1);
        mapPage.put("limits", 10);
        int rows = 0;
        mapPage.put("rows", rows);
        List<Msg> select = msgService.select(mapPage);
        for (Msg msg : select) {
            System.out.println(msg);
        }
    }

    @Test
    public void getPojo() {
        String username="陈小林";
        String password="15983019221";
        String s = Utils.shiroMd5(username, password);
        System.out.println(s);
    }

}
