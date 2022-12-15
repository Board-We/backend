package com.boardwe.boardwe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class ImageConfig {

    @Bean
    public Path rootDir(@Value("${file.upload-dir}") String rootDirValue) {
        Path rootDir = Paths.get(rootDirValue)
                .toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(rootDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootDir;
    }

    @Bean
    public String cdnUrl(@Value("${server.port}") String port) throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        return String.format("%s:%s", ipAddress.getHostAddress(), port);
    }

}
