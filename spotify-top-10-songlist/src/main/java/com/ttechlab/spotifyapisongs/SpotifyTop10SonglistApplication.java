package com.ttechlab.spotifyapisongs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpotifyTop10SonglistApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyTop10SonglistApplication.class, args);
	}
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
