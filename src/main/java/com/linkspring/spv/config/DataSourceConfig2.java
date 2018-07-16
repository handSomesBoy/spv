package com.linkspring.spv.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @项目名称：spv-common
 * @包名：com.spv.manage.config
 * @类描述：数据源配置
 * @创建人：spv
 * @创建时间：2018-02-27 13:33
 * @version：V1.0
 */
//@Configuration
//指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate
//@MapperScan(basePackages = "com.linkspring.spv.test1", sqlSessionTemplateRef  = "test1SqlSessionTemplate")
public class DataSourceConfig2 {
	/**
	 * 创建datasource对象
	 * @return
	 *//*
	@Bean(name = "test1DataSource")
	@ConfigurationProperties(prefix = "slave.datasource.test1")// prefix值必须是application.properteis中对应属性的前缀
	public DataSource test1DataSource() {
		return DataSourceBuilder.create().build();
	}

	*//**
	 * 创建sql工程
	 * @param dataSource
	 * @return
	 * @throws Exception
	 *//*
	@Bean(name = "test1SqlSessionFactory")
	public SqlSessionFactory test1SqlSessionFactory(@Qualifier("test1DataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//对应mybatis.type-aliases-package配置
		bean.setTypeAliasesPackage("com.linkspring.spv.pojo");
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		//开启驼峰映射
		bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return bean.getObject();
	}

	*//**
	 * 配置事务管理
	 * @param dataSource
	 * @return
	 *//*
	@Bean(name = "test1TransactionManager")
	public DataSourceTransactionManager test1TransactionManager(@Qualifier("test1DataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	*//**
	 * sqlSession模版，用于配置自动扫描pojo实体类
	 * @param sqlSessionFactory
	 * @return
	 * @throws Exception
	 *//*
	@Bean(name = "test1SqlSessionTemplate")
	public SqlSessionTemplate test1SqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}*/
}
