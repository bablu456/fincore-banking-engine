package com.codewithbablu.fincore.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    // Beast Mode: 10 Threads ready for War
    @Bean(name = "taskExecutor")
    public ExecutorService taskExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
