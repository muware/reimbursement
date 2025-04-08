package com.uiowa.evaluation.reimbursement.configuration;

import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This is where the database is configured.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
    public static final String ENTITY_PACKAGE = "com.uiowa.evaluation.reimbursement";

    /**
     * Using the embedded H2 database engine for data storage.
     * Database contents don't get committed to the filesystem, 
     * which means that once the web server is terminated, 
     * the data is gone.
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        EntityManagerFactoryBuilder builder, 
        DataSource dataSource
    ) {
        return builder
            .dataSource(dataSource)
            .packages(JpaConfiguration.ENTITY_PACKAGE)
            .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
        LocalContainerEntityManagerFactoryBean entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

}
