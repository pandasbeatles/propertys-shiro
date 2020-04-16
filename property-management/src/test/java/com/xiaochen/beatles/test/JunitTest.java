package com.xiaochen.beatles.test;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JunitTest {

    ApplicationContext context = null;

    @Before
    public void factory() {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }


    @Test
    public void getbeans() {
        System.out.println(context);

    }

    @Test
    public void getPojo() {
        System.out.println(1);
    }


}
