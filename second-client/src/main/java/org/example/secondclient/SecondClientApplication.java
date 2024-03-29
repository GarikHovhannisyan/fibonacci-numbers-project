package org.example.secondclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SecondClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondClientApplication.class, args);
    }

}
