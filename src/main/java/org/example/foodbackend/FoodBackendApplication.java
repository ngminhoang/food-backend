package org.example.foodbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = "org.example.foodbackend.entities")
public class FoodBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodBackendApplication.class, args);
    }

}
