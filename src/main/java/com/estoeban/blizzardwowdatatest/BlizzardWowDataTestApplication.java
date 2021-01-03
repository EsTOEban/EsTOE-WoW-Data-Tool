package com.estoeban.blizzardwowdatatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BlizzardWowDataTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlizzardWowDataTestApplication.class, args);
    }
}
