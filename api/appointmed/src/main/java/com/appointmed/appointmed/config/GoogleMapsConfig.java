package com.appointmed.appointmed.config;

import com.appointmed.appointmed.config.secrets.GoogleSecrets;
import com.google.maps.GeoApiContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(GoogleSecrets.class)
public class GoogleMapsConfig {

    private final GoogleSecrets googleSecrets;

    @Bean
    public GeoApiContext createGeoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey(googleSecrets.getMapsApiKey())
                .build();
    }

}

//@Configuration
//public class GoogleMapsConfig {
//
//    @Bean
//    public GeoApiContext createGeoApiContext() {
//        return new GeoApiContext.Builder()
//                .apiKey("API KEY HARDCODED HERE, VERY BAD!")
//                .build();
//    }
//}

