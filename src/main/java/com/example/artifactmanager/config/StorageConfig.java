package com.example.artifactmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class StorageConfig {

    @Value("${app.file.storage.location}")
    private String storageLocation;

    @Bean
    public void initStorageLocation() throws IOException {
        Path uploadDir = Paths.get(storageLocation);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }
}

