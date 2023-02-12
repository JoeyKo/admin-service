package com.example.admin;

import com.example.admin.controller.UserController;
import com.example.admin.storage.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MyApplication {

  public static void main(String[] args) {
    // 返回IOC容器
    ConfigurableApplicationContext run = SpringApplication.run(MyApplication.class, args);

    // 获取组件
    String[] names = run.getBeanDefinitionNames();
    for(String name : names) {
      System.out.println(name);
    }
  }

}