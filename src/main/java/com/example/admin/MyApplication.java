package com.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
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