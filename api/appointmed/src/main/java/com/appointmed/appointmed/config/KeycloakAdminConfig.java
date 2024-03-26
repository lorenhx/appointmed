package com.appointmed.appointmed.config;

import com.appointmed.appointmed.config.secrets.KeycloakSecrets;
import lombok.Getter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@EnableConfigurationProperties(KeycloakSecrets.class)
public class KeycloakAdminConfig {

    private final String realm;
    private final String clientId;

    @Autowired
    private final KeycloakSecrets keycloakSecrets;

    public KeycloakAdminConfig(@Value("${keycloak.realm}") String realm, @Value("${keycloak.clientId}") String clientId, KeycloakSecrets keycloakSecrets) {
        this.realm = realm;
        this.clientId = clientId;
        this.keycloakSecrets = keycloakSecrets;
    }

    @Bean
    public Keycloak createKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm(this.realm)
                .username(keycloakSecrets.getUsername())
                .password(keycloakSecrets.getPassword())
                .clientId("admin-cli")
                .build();
    }

}