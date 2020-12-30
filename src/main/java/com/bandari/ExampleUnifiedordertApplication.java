package com.bandari;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(value = "com.bandari.dao")
public class ExampleUnifiedordertApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExampleUnifiedordertApplication.class, args);
  }

}
