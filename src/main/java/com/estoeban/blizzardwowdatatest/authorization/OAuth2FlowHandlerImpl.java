package com.estoeban.blizzardwowdatatest.authorization;

import com.estoeban.blizzardwowdatatest.config.AppConfig;
import com.estoeban.blizzardwowdatatest.models.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.www.protocol.http.Handler;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.time.Instant;
import java.util.Base64;


/**
 * {@inheritDoc}
 */
@Service
@Log4j2
public class OAuth2FlowHandlerImpl implements OAuth2FlowHandler {
    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;

    // To allow testing of the URL/Connection
    private URLStreamHandler urlStreamHandler = new Handler();

    private String token = null;
    private Instant tokenExpiry = null; // Instant when the token will expire

    private final Object tokenLock = new Object();

    @Autowired
    public OAuth2FlowHandlerImpl(AppConfig appConfig,
        ObjectMapper objectMapper) {
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken() throws IOException {
        if(isTokenInvalid()){
            log.trace("---");
            log.trace("Fetching/Creating token.");

            String encodedCredentials = Base64.getEncoder().encodeToString(String.format("%s:%s", appConfig.getClientId(), appConfig.getClientSecret()).getBytes(appConfig.getEncoding()));

            // ------------------------------------------------- Allows testing/mocking of the URL connection object
            CookieHandler.setDefault(new CookieManager());
//            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = null;

            try {
                log.info(appConfig.getTokenUrl());
                URL url = new URL(appConfig.getTokenUrl().toString());
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", String.format("Basic %s", encodedCredentials));
                con.setDoOutput(true);
                con.getOutputStream().write("grant_type=client_credentials".getBytes(appConfig.getEncoding()));

                int responseCode = con.getResponseCode();
                log.trace(String.format("Sent 'POST' to %s requesting access token via client credentials grant type.", url));
                log.trace(String.format("Result code: %s", responseCode));

                String response = IOUtils.toString(con.getInputStream(), appConfig.getEncoding());

                log.trace(String.format("Response: %s", response));

                try {
                    // Reads the JSON response and converts it to TokenResponse class or throws an exception
                    TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);
                    synchronized (tokenLock) {
                        tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpires_in());
                        token = tokenResponse.getAccess_token();
                    }
                } catch (MismatchedInputException mismatchedInputException) {
                    // do nothing
                    log.info("Token Response: " + response);
                    log.info("Token Response Code: " + responseCode);
                }
                log.trace("---");
            } finally {
                if(con != null){
                    con.disconnect();
                }
            }
        }
        synchronized (tokenLock){
            return token;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTokenInvalid(){
        synchronized (tokenLock) {
            if (token == null) {
                return true;
            }
            if (tokenExpiry == null) {
                return true;
            }
            return Instant.now().isAfter(tokenExpiry);
        }
    }
}
