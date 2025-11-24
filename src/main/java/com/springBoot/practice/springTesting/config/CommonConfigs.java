package com.springBoot.practice.springTesting.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfigs {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
