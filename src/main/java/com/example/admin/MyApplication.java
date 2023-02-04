package com.example.admin;

import com.example.admin.controller.UserController;
import com.example.admin.storage.StorageProperties;
import com.example.admin.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MyApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  public static void main(String[] args) {
    // 返回IOC容器
    ConfigurableApplicationContext run = SpringApplication.run(MyApplication.class, args);

    // 获取组件
    String[] names = run.getBeanDefinitionNames();
    for(String name : names) {
      System.out.println(name);
    }
  }

  @Bean
  CommandLineRunner init(StorageService storageService) {
    return (args) -> {
      storageService.init();
    };
  }

}