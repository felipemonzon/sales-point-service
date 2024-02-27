package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.ProductTypeRequest;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test de las apis de tipos de producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 26 feb., 2024
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductTypeControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta base de tipos de producto. */
  private static final String PRODUCT_TYPE_BASE_PATH = "/products/type";

  @Test
  @DisplayName("GET /products/type success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(PRODUCT_TYPE_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Tipos de producto encontrados en test {}", response);
  }

  @Order(1)
  @ParameterizedTest
  @CsvSource(value = {"Libros", "Libros"})
  @DisplayName("POST /products/type success")
  void save(String description) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(PRODUCT_TYPE_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(this.getProductTypeRequest(description))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Order(2)
  @ParameterizedTest
  @CsvSource(value = {"1", "100"})
  @DisplayName("PUT /products/type")
  void update(long id) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(PRODUCT_TYPE_BASE_PATH + "/" + id)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getProductTypeRequest("Otros"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private ProductTypeRequest getProductTypeRequest(String description) {
    ProductTypeRequest request = new ProductTypeRequest();
    request.setName(description);
    return request;
  }
}
