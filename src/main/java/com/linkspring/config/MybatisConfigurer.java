package com.linkspring.config;

import com.linkspring.spv.pojo.User;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
/** test
 * 非 启动类 当前路径下的配置类，不会自动配置，但可以使用AutoConfigureAfter 设置顺序
 *并且在 spring.factories 里面配置启动
 *
 * */
@Configuration
@AutoConfigureAfter(MybatisConfigurer2.class)
public class MybatisConfigurer {
    public MybatisConfigurer() {
        System.err.println("自定义配置类1");
    }
    @PostConstruct
    public void  MybatisConfigurer22() {
        System.err.println("自定义配置类1");
    }
}