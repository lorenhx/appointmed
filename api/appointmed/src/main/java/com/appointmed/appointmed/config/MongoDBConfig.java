package com.appointmed.appointmed.config;

import com.appointmed.appointmed.config.secrets.MongoDBSecrets;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@EnableConfigurationProperties(MongoDBSecrets.class)
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    private final MongoDBSecrets mongoDBSecrets;
    private final String HOST;
    private final int PORT;
    private final String DATABASE;

    public MongoDBConfig(MongoDBSecrets mongoDBSecrets,
                         @Value("${custom-env.mongodb.host}") String host,
                         @Value("${custom-env.mongodb.port}") int port,
                         @Value("${custom-env.mongodb.database}") String database) {
        this.mongoDBSecrets = mongoDBSecrets;
        this.HOST = host;
        this.PORT = port;
        this.DATABASE = database;
    }

    @NotNull
    @Override
    protected String getDatabaseName() {
        return DATABASE;
    }
    @NotNull
    @Override
    public MongoClientSettings mongoClientSettings() {
        String username = mongoDBSecrets.getUsername();
        String password = mongoDBSecrets.getPassword();

        String connectionString = String.format("mongodb://%s:%s@%s:%d/%s", username, password, HOST, PORT, DATABASE);
        System.out.println("CONNECTION_STRING " + connectionString);
        ConnectionString connString = new ConnectionString(connectionString);

        return MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();
    }
}
