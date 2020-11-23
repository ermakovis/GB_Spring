package ru.geekbrains.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan
public class AppConfig {
    @Bean(name = "Connection")
    public Connection connection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/network_chat",
                "erm",
                "gfhjkm123");
    }
}
