package com.appointmed.appointmed.config;

import com.appointmed.appointmed.config.secrets.KeycloakSecrets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakSecrets.class)
public class KeycloakAdminConfig {

    private final String URL;
    private final KeycloakSecrets keycloakSecrets;
    public KeycloakAdminConfig( @Value("${custom-env.keycloak.url}") String url, KeycloakSecrets keycloakSecrets)
    {
        this.URL=url;
        this.keycloakSecrets=keycloakSecrets;
    }

    @Bean
    public Keycloak createKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(URL)
                .realm("master")
                .username(keycloakSecrets.getUsername())
                .password(keycloakSecrets.getPassword())
                .clientId("admin-cli")
                .build();
    }

}