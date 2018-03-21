package com.thinker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.thinker.auth.dao.MysqlMapper;
import com.thinker.easylife.dao.EasyLifeMapper;
import com.thinker.video.dao.ArdVideoMapper;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@MapperScan(basePackageClasses = { MysqlMapper.class, EasyLifeMapper.class,
		ArdVideoMapper.class })
// @ImportResource({ "classpath:redis/spring-redis-cluster.xml",
// "classpath:hessian/hessian-client.xml" })
public class ArdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArdApplication.class, args);
	}
}
