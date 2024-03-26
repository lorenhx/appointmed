package com.appointmed.appointmed.config;

import com.appointmed.appointmed.config.secrets.MongoDBSecrets;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MongoDBSecrets.class)
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    private final MongoDBSecrets mongoDBSecrets;
    @NotNull
    @Override
    protected String getDatabaseName() {
        return "appointmed";
    }

    @NotNull
    @Override
    public MongoClientSettings mongoClientSettings() {
        String username = mongoDBSecrets.getUsername();
        String password = mongoDBSecrets.getPassword();
        ConnectionString connectionString = new ConnectionString("mongodb://"+username+":"+password+"@"+"localhost:27017/appointmed");
        return MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
    }


}
