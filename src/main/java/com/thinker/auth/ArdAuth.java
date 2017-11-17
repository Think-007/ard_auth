package com.thinker.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.thinker.auth.dao.MysqlMapper;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@MapperScan(basePackageClasses = { MysqlMapper.class })
// @ImportResource({ "classpath:redis/spring-redis-cluster.xml",
// "classpath:hessian/hessian-client.xml" })
public class ArdAuth {

	public static void main(String[] args) {
		SpringApplication.run(ArdAuth.class, args);
	}
}
