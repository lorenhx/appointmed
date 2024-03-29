package com.appointmed.appointmed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.Socket;

@SpringBootApplication
public class AppointmedApplication {

    public static void main(String[] args) {

        SpringApplication.run(AppointmedApplication.class, args);
    }
}

