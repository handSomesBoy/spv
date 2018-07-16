package com.linkspring.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

import javax.annotation.PostConstruct;
/**
 * 非 启动类 当前路径下的配置类，不会自动配置，但可以使用AutoConfigureAfter 设置顺序
 *并且在 spring.factories 里面配置启动
 *
 * */
@Configuration
@AutoConfigureAfter(MapperAutoConfiguration.class)
public class MybatisConfigurer2 {
    public MybatisConfigurer2() {
        System.err.println("自定义配置类2");
    }
    @PostConstruct
    public void  MybatisConfigurer22() {
        System.err.println("自定义配置类2");
    }
    /*@Resource
    private DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.linkspring.spv.pojo");

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Configuration
    @AutoConfigureAfter(MybatisConfigurer.class)
    public static class MyBatisMapperScannerConfigurer {

        @Bean
        public static MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
            mapperScannerConfigurer.setBasePackage("com.linkspring.spv.dao.*");
            //配置通用mappers
            Properties properties = new Properties();
            properties.setProperty("mappers", "{tk.mybatis.mapper.common.Mapper,com.linkspring.spv.common.MyMapper}");
            properties.setProperty("notEmpty", "false");
            properties.setProperty("IDENTITY", "MYSQL");
            mapperScannerConfigurer.setProperties(properties);

            return mapperScannerConfigurer;
        }

    }*/
}