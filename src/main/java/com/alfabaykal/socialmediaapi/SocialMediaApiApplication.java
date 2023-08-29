package com.alfabaykal.socialmediaapi;

import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SocialMediaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public EmailValidator emailValidator() {
        return EmailValidator.getInstance();
    }

}
