package com.moontech.salespoint.apis;

import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test de las apis de producto.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 27 feb., 2024
 */
@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/product-script-test.sql"})
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Ruta base de producto. */
  private static final String PRODUCT_BASE_PATH = "/products";

  @Test
  @DisplayName("GET /products success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(PRODUCT_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Productos encontrados en test {}", response);
  }

  @Order(1)
  @ParameterizedTest
  @CsvSource(
      value = {
        "producto 1, SUPkkI4KwH8ykQfq94sl, 1",
        "Libros, SUPkkI4KwH8ykQfq94100, 7",
        "Libros, SUPkkI4KwH8ykQfq94100, 1",
        "Small Metal Ball, SUPkkI4KwH8ykQfq94sl, 1"
      })
  @DisplayName("POST /products success")
  void save(String name, String supplier, String productType) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart(PRODUCT_BASE_PATH)
                .file(this.getImg())
                .param("name", name)
                .param("unitPrice", BigDecimal.ONE.toPlainString())
                .param("sellPrice", BigDecimal.ONE.toPlainString())
                .param("supplierId", supplier)
                .param("productTypeId", productType)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Order(2)
  @ParameterizedTest
  @CsvSource(value = {"PROfZWswkooMhZ5AL100", "PROfZWswkooMhZ5ALWfO"})
  @DisplayName("PUT /products")
  void update(String id) throws Exception {
    MockMultipartHttpServletRequestBuilder multipart =
        (MockMultipartHttpServletRequestBuilder)
            MockMvcRequestBuilders.multipart(PRODUCT_BASE_PATH + "/" + id)
                .with(
                    request -> {
                      request.setMethod(HttpMethod.PUT.name());
                      return request;
                    });
    this.mockMvc
        .perform(
            multipart
                .file(this.getImg())
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "Libro 1")
                .param("unitPrice", BigDecimal.ONE.toPlainString())
                .param("sellPrice", BigDecimal.ONE.toPlainString())
                .param("supplierId", "SUPkkI4KwH8ykQfq94sl")
                .param("productTypeId", "1"))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private MockMultipartFile getImg() throws IOException {
    Resource fileResource = new ClassPathResource("/test.jpeg");
    return new MockMultipartFile(
        "image",
        fileResource.getFilename(),
        MediaType.MULTIPART_FORM_DATA_VALUE,
        fileResource.getInputStream());
  }
}
