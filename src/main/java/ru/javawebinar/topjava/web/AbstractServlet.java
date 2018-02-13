package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * V.B. on
 * 13-Feb-18.
 */
public class AbstractServlet extends HttpServlet {

    protected AutowireCapableBeanFactory ctx;
    @Override
    public void init() throws ServletException {
        super.init();
        ctx = new ClassPathXmlApplicationContext("spring/spring-app.xml").getAutowireCapableBeanFactory();
        //The following line does the magic
        ctx.autowireBean(this);

    }
}
