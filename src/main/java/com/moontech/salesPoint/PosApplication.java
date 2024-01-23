package com.moontech.salesPoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PosApplication {

  public static void main(String[] args) {
    SpringApplication.run(PosApplication.class, args);
  }
}
