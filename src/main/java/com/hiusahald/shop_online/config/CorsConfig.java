package com.hiusahald.shop_online.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${app.frontend.origin}")
    private String frontendOrigin;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(getHeaders());
        config.setAllowedMethods(getMethods());
        config.setAllowedOrigins(getOrigins());
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    private List<String> getHeaders() {
        List<String> list = new ArrayList<>();
        list.add(HttpHeaders.ORIGIN);
        list.add(HttpHeaders.CONTENT_TYPE);
        list.add(HttpHeaders.AUTHORIZATION);
        list.add(HttpHeaders.ACCEPT);
        return list;
    }

    private List<String> getMethods() {
        List<String> list = new ArrayList<>();
        list.add(HttpMethod.GET.name());
        list.add(HttpMethod.POST.name());
        list.add(HttpMethod.PUT.name());
        list.add(HttpMethod.DELETE.name());
        return list;
    }

    private List<String> getOrigins() {
        List<String> list = new ArrayList<>();
        list.add(frontendOrigin);
        return list;
    }

}
