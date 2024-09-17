package com.hiusahald.shop_online.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${app.storage.cloudinary.cloud-name}")
    private String cloudName;
    @Value("${app.storage.cloudinary.api-key}")
    private String apiKey;
    @Value("${app.storage.cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("cloud_name", cloudName);
        configs.put("api_key", apiKey);
        configs.put("api_secret", apiSecret);
        configs.put("secure", true);
        return new Cloudinary(configs);
    }

}
