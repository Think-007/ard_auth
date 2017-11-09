package com.thinker.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.thinker.auth.dao.MysqlMapper;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackageClasses = { MysqlMapper.class })
public class ArdAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArdAuthApplication.class, args);
	}
}
