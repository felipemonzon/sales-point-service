package com.moontech.salespoint.configuration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Configuración base para los test.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 31 jan., 2024
 */
@Testcontainers
public abstract class MysqlBaseConfigurationTest {
  /** Contenedor de Mysql. */
  private static final MySQLContainer<?> mysqlContainer;

  static {
    mysqlContainer =
        new MySQLContainer<>("mysql:latest")
            .withDatabaseName("salesPoint")
            .withUsername("root")
            .withPassword("root")
            .withReuse(Boolean.TRUE);
    mysqlContainer.start();
  }

  @DynamicPropertySource
  static void registerMySQLProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mysqlContainer::getUsername);
    registry.add("spring.datasource.password", mysqlContainer::getPassword);
  }
}
