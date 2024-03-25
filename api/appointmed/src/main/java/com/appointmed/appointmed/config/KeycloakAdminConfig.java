package com.appointmed.appointmed.config;

import lombok.Getter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KeycloakAdminConfig {

    private final String realm;
    private final String clientId;

    public KeycloakAdminConfig(@Value("${keycloak.realm}") String realm, @Value("${keycloak.clientId}") String clientId) {
        this.realm = realm;
        this.clientId = clientId;
    }

    @Bean
    public Keycloak createKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm(this.realm)
                .username("admin")
                .password("admin")
                .clientId("admin-cli")
                .build();
    }

}