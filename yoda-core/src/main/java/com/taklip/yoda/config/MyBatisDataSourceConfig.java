package com.taklip.yoda.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.taklip.yoda.model.BaseEntity;

/**
 * @author askar
 */
@Configuration
@MapperScan("com.taklip.yoda.mapper")
@EnableTransactionManagement
public class MyBatisDataSourceConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.taklip.yoda.model");
        bean.setTypeAliasesSuperType(BaseEntity.class);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mappings/*.xml"));
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));

//		// 配置分页插件，详情请查阅官方文档
//		PageHelper pageHelper = new PageHelper();
//		Properties properties = new Properties();
//		properties.setProperty("pageSizeZero", "true");// 分页尺寸为0时查询所有纪录不再执行分页
//		properties.setProperty("reasonable", "true");// 页码<=0 查询第一页，页码>=总页数查询最后一页
//		properties.setProperty("supportMethodsArguments", "true");// 支持通过 Mapper 接口参数来传递分页参数
//		pageHelper.setProperties(properties);
//
//		// 添加插件
//		factory.setPlugins(new Interceptor[] { pageHelper });

        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//	@Bean
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//		mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);
//
//		// 配置通用Mapper，详情请查阅官方文档
//		Properties properties = new Properties();
//		properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
//		properties.setProperty("notEmpty", "false");// insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str
//													// != ''
//		properties.setProperty("IDENTITY", "MYSQL");
//		mapperScannerConfigurer.setProperties(properties);
//
//		return mapperScannerConfigurer;
//	}
}