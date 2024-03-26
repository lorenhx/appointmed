package com.appointmed.appointmed.config.secrets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties("google")
public class GoogleSecrets {
    private String mapsApiKey;
    private String oauth2ClientSecret;
}