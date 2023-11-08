package com.ttechlab.spotifyapisongs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SpotifyService {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;

    public SpotifyService(RestTemplate restTemplate,
                          @Value("${spotify.api.base-url}") String baseUrl,
                          @Value("${spotify.api.client-id}") String clientId,
                          @Value("${spotify.api.client-secret}") String clientSecret) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
    private static class AccessTokenResponse {
        private String access_token;
        private String token_type;
        private int expires_in;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getToken_type() {
            return token_type;
        }

        public void setToken_type(String token_type) {
            this.token_type = token_type;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }
    }

    public List<String> getTopTracks() {
        String accessToken = obtainAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/v1/me/top/tracks", HttpMethod.GET, entity, String.class);

        List<String> topTracks = parseTopTracksResponse(response.getBody());

        return topTracks;
    }

    private String obtainAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String credentials = clientId + ":" + clientSecret;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        headers.set("Authorization", "Basic " + base64Credentials);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            "https://accounts.spotify.com/api/token",
            HttpMethod.POST,
            entity,
            String.class
        );

        System.out.println("Request URL: " + "https://accounts.spotify.com/api/token");
        System.out.println("Request headers: " + headers);
        System.out.println("Request body: " + requestBody);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());

        if (response.getStatusCode() != HttpStatus.OK) {
            System.out.println("Error obtaining access token. Status code: " + response.getStatusCode());
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AccessTokenResponse tokenResponse = objectMapper.readValue(response.getBody(), AccessTokenResponse.class);
            return tokenResponse.getAccess_token();
        } catch (IOException e) {
            System.out.println("Error parsing access token response: " + e.getMessage());
            return null;
        }
    }

    private List<String> parseTopTracksResponse(String responseBody) {
        List<String> topTracks = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(responseBody);

            JsonNode items = responseJson.get("items");
            for (JsonNode item : items) {
                JsonNode track = item.get("track");
                String trackName = track.get("name").asText();
                topTracks.add(trackName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return topTracks;
    }

    private static class AccessTokenResponse1 {
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }
    }
}
