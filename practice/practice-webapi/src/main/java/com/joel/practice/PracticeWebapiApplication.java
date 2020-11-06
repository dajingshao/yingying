package com.joel.practice;

import com.joel.practice.common.util.UniqueBeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(nameGenerator = UniqueBeanNameGenerator.class)
@ConfigurationPropertiesScan
public class PracticeWebapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeWebapiApplication.class, args);
    }

}
