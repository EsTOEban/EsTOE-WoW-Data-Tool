package com.estoeban.blizzardwowdatatest.authorization;

import com.estoeban.blizzardwowdatatest.config.AppConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

/**
 * {@inheritDoc}
 */
@Service
@Log4j2
public class ApiCrawlerImpl implements ApiCrawler {
    private final OAuth2FlowHandler oAuth2FlowHandler;

    private final AppConfig appConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ApiCrawlerImpl(OAuth2FlowHandler oAuth2FlowHandler,
        AppConfig appConfig) {
        this.oAuth2FlowHandler = oAuth2FlowHandler;
        this.appConfig = appConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getDataFromRelativePath(final String relativePath, final Map<String, String> params, Class<T> klazz) throws IOException, URISyntaxException {
        return getDataFromRelativePath(relativePath, params, klazz, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getDataFromRelativePath(final String relativePath, final Map<String, String> params, Class<T> klazz, final Boolean sendInHeader) throws IOException, URISyntaxException {
        String token = oAuth2FlowHandler.getToken();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUri(appConfig.getBaseUrl().toURI())
                .path(relativePath);

        params.forEach(uriBuilder::queryParam);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(sendInHeader) {
            log.trace(String.format("Sending token as header 'Authorization: Bearer %s'.", token));
            headers.set("Authorization", String.format("Bearer %s", token));
        } else {
            log.trace(String.format("Sending token %s as parameter access_token.", token));
            uriBuilder.queryParam("access_token", token);
        }

        URI uri = uriBuilder.build().toUri();

        log.trace(String.format("URL compiled: %s", uri.toString()));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        log.info(String.format("Performing %s request to %s using token %s", HttpMethod.GET, uri, token));

        ResponseEntity<T> result = restTemplate.exchange(uri, HttpMethod.GET, entity, klazz);
        log.trace(String.format("Result status: %s", result.getStatusCodeValue()));

        log.trace(String.format("Result: %s", result));

        return result.getBody();
    }
}
