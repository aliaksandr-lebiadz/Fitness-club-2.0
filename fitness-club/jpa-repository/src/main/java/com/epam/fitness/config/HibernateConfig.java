package com.epam.fitness.config;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Lazy
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class HibernateConfig {

    private static final String ENTITY_PACKAGE = "com.epam.fitness.entity";

    @Value("${database.driver_name}")
    private String driverName;
    @Value("${database.url}")
    private String url;
    @Value("${database.user}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.session_context}")
    private String sessionContext;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan(ENTITY_PACKAGE);
        emf.setDataSource(getDataSource());
        emf.setJpaVendorAdapter(createJpaVendorAdapter());
        emf.setJpaProperties(hibernateProperties());
        emf.afterPropertiesSet();
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
    private JpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, dialect);
        properties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, sessionContext);
        return properties;
    }

    private DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
