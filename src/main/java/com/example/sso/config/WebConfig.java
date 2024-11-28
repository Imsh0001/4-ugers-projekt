package com.example.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Allow all routes under "/api/"
                .allowedOrigins("http://localhost:63342") // Allow frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these HTTP methods
                .allowedHeaders("*"); // Allow all headers
    }
}
