package com.appointmed.appointmed.config;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfig {
    @Bean
    public GeoApiContext createGeoApiContext() {
        return new GeoApiContext.Builder()
                .apiKey("")
                .build();
    }
}
