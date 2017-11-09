package com.thinker.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * PropertyConfgi.java
 * <p>
 * Created by LPF on 2017/10/22.
 * <p>
 * 比你优秀的人还在努力，所以你不能懒惰。
 * <p>
 * Copyright (c) 2017, LPF 版权所有!
 */
@Configuration
@PropertySource({ "classpath:config/other/system.properties" })
public class PropertyConfgi {
}
