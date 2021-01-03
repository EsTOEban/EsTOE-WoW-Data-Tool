package com.estoeban.blizzardwowdatatest.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.net.URL;

/**
 * Reads in the application.yml and assigns values to the member variables.
 *
 * As a service, it is available to other aspects of the application, to derive data from the application.yml.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "oauth2")
@Data
public class AppConfig {
    private URL tokenUrl;
    private String encoding;
    private URL baseUrl;
    private URL baseImageUrl;
    private Float compressionQuality;

    // Blizzard Client Information
    private String clientId;
    private String clientSecret;

    @PostConstruct
    public void init() {
//        log.info(this.toString());

        Assert.notNull(clientId, "Client ID Variable must be specified.");
        Assert.hasText(clientId, "Client ID Variable must be specified.");

        Assert.notNull(clientSecret, "Client Secret Variable must be specified.");
        Assert.hasText(clientSecret, "Client Secret Variable must be specified.");
    }
}
