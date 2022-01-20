package com.rosouza.school.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.rosouza.school.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
