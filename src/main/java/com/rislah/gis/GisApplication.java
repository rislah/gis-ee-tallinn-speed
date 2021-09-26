package com.rislah.gis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationAutoConfig.class)
public class GisApplication {

    public static void main(String[] args) {
        SpringApplication.run(GisApplication.class, args);
    }
}
