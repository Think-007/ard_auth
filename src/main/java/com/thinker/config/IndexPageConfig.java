package com.thinker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * IndexConfig.java
 * <p>
 * Created by LPF on 2017/10/22.
 * <p>
 * 比你优秀的人还在努力，所以你不能懒惰。
 * <p>
 * Copyright (c)  2017, LPF 版权所有!
 */
@Configuration
public class IndexPageConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

}
