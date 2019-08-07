package com.liwinon.phoneScanning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecond",
        transactionManagerRef = "transactionManagerSecond",
        basePackages = {"com.liwinon.phoneScanning.QiyeWX.dao.secondRepo"} // 指定该数据源操作的DAO接口包
)
public class SecondConfig {
    @Autowired
    @Qualifier("secondDataSource")
    private DataSource secondDataSource;
    @Autowired
    private Environment env;


    @Bean(name = "entityManagerSecond")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactorySecond(builder).getObject().createEntityManager();
    }
    @Bean(name = "entityManagerFactorySecond")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecond(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(secondDataSource)
                .properties(getVendorProperties())
                .packages("com.liwinon.phoneScanning.QiyeWX.entity.second")         //设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit")   //????????
                .build();
    }
    private Map getVendorProperties() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        properties.put("hibernate.ddl-auto",
                "none");
        properties.put("hibernate.physical-strategy",
                "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        return properties;
    }

    @Bean(name = "transactionManagerSecond")
    PlatformTransactionManager transactionManagerSecond(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactorySecond(builder).getObject());
    }

}
