package edu.java.spring;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class AppContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("---------->Da khoi tao ung dung");

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("----------> Da huy ung dung");
    }
}
