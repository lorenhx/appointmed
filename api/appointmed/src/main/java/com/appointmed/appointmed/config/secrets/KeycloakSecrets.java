package com.appointmed.appointmed.config.secrets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties("keycloak")
public class KeycloakSecrets {
    String username;
    String password;
}
