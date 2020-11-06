package com.joel.practice.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@EnableConfigurationProperties(IgnoreUrlsProperties.class)
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsProperties {
    private List<String> urls = new ArrayList<>();
}
