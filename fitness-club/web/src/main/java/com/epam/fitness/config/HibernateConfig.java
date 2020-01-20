package com.epam.fitness.config;

import com.epam.fitness.entity.GymMembership;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Lazy
@EnableTransactionManagement
public class HibernateConfig {

    private static final String ENTITY_PACKAGE = "com.epam.fitness.entity";

    @Autowired
    private DataSource dataSource;

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.session_context}")
    private String sessionContext;

    @Bean
    public SessionFactory sessionFactory() {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        Map<String, String> settings = getSettings();

        registryBuilder.applySettings(settings);
        registryBuilder.applySetting(Environment.DATASOURCE, dataSource);
        StandardServiceRegistry registry = registryBuilder.build();

        MetadataSources sources = new MetadataSources(registry);
        configureSources(sources);
        Metadata metadata = sources
                .getMetadataBuilder()
                .build();

        return metadata
                .getSessionFactoryBuilder()
                .build();
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory());
        return transactionManager;
    }

    private Map<String, String> getSettings(){
        Map<String, String> settings = new HashMap<>();
        settings.put(Environment.DIALECT, dialect);
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, sessionContext);
        return settings;
    }

    private void configureSources(MetadataSources sources){
        sources.addPackage(ENTITY_PACKAGE);
        sources.addAnnotatedClass(User.class);
        sources.addAnnotatedClass(Order.class);
        sources.addAnnotatedClass(GymMembership.class);
    }
}
