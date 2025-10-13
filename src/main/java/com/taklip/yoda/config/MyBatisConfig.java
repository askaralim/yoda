package com.taklip.yoda.config;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.taklip.yoda.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Configuration
@MapperScan("com.taklip.yoda.mapper")
@EnableTransactionManagement
@Slf4j
public class MyBatisConfig {
    // @Autowired
    // private IdService idService;

    // @Bean
    // public IdentifierGenerator identifierGenerator() {
    // return new IdentifierGenerator() {
    // @Override
    // public Number nextId(Object entity) {
    // Number id = idService.generateId();
    // log.info("nextId: {}", id);
    // return id;
    // }。，/
    // };
    // }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                log.info("authentication: {}", authentication);
                if (authentication != null && authentication.getPrincipal() instanceof User) {
                    User user = (User) authentication.getPrincipal();
                    this.strictInsertFill(metaObject, "createBy", Long.class, user.getId());
                    this.strictInsertFill(metaObject, "updateBy", Long.class, user.getId());
                }
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.getPrincipal() instanceof User) {
                    User user = (User) authentication.getPrincipal();
                    this.strictUpdateFill(metaObject, "updateBy", Long.class, user.getId());
                }
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            }
        };
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    // @Bean
    // public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource")
    // DataSource dataSource)
    // throws Exception {
    // SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

    // bean.setDataSource(dataSource);
    // bean.setTypeAliasesPackage("com.taklip.yoda.model");
    // bean.setTypeAliasesSuperType(BaseEntity.class);
    // bean.setMapperLocations(
    // new
    // PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mappings/*.xml"));
    // bean.setConfigLocation(
    // new
    // PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));

    // // // 配置分页插件，详情请查阅官方文档
    // // PageHelper pageHelper = new PageHelper();
    // // Properties properties = new Properties();
    // // properties.setProperty("pageSizeZero", "true");// 分页尺寸为0时查询所有纪录不再执行分页
    // // properties.setProperty("reasonable", "true");// 页码<=0 查询第一页，页码>=总页数查询最后一页
    // // properties.setProperty("supportMethodsArguments", "true");// 支持通过 Mapper
    // // 接口参数来传递分页参数
    // // pageHelper.setProperties(properties);
    // //
    // // // 添加插件
    // // factory.setPlugins(new Interceptor[] { pageHelper });

    // return bean.getObject();
    // }

    // @Bean(name = "sqlSessionTemplate")
    // public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory")
    // SqlSessionFactory sqlSessionFactory) {
    // return new SqlSessionTemplate(sqlSessionFactory);
    // }

    // @Bean(name = "transactionManager")
    // public DataSourceTransactionManager
    // transactionManager(@Qualifier("dataSource") DataSource dataSource) {
    // return new DataSourceTransactionManager(dataSource);
    // }

    // @Bean
    // public MapperScannerConfigurer mapperScannerConfigurer() {
    // MapperScannerConfigurer mapperScannerConfigurer = new
    // MapperScannerConfigurer();
    // mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
    // mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);
    //
    // // 配置通用Mapper，详情请查阅官方文档
    // Properties properties = new Properties();
    // properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
    // properties.setProperty("notEmpty", "false");// insert、update是否判断字符串类型!='' 即
    // test="str != null"表达式内是否追加 and str
    // // != ''
    // properties.setProperty("IDENTITY", "MYSQL");
    // mapperScannerConfigurer.setProperties(properties);
    //
    // return mapperScannerConfigurer;
    // }
}