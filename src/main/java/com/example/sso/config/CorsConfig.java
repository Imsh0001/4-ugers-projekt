package com.example.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:63342"); // Din frontend URL
        config.addAllowedMethod("*");                     // Tillad alle metoder
        config.addAllowedHeader("*");                     // Tillad alle headers
        config.setAllowCredentials(true);                 // Tillad cookies og credentials

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
