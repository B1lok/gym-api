package com.example.gymapi.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestContextInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {


  private static PostgreSQLContainer postgreSQLContainer =
      (PostgreSQLContainer) new PostgreSQLContainer("postgres:16")
          .withDatabaseName("gym")
          .withUsername("postgres")
          .withPassword("postgres")

          .withStartupTimeout(Duration.ofSeconds(600));

  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

    if (!postgreSQLContainer.isRunning()) postgreSQLContainer.start();
    TestPropertyValues.of(
        "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
        "spring.datasource.username=" + postgreSQLContainer.getUsername(),
        "spring.datasource.password=" + postgreSQLContainer.getPassword()
    ).applyTo(configurableApplicationContext.getEnvironment());

  }
}
