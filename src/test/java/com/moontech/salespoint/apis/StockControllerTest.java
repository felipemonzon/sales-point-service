package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.StockRequest;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test de las apis de inventario.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 28 feb., 2024
 */
@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@Sql(scripts = {"/product-stock-script-test.sql"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockControllerTest extends MysqlBaseConfigurationTest {
  /** MockMvc. */
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta base de inventario. */
  private static final String STOCK_BASE_PATH = "/products/stock";

  @Test
  @DisplayName("GET /products/stock success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(STOCK_BASE_PATH+ "?page=1")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("inventario encontrados en test {}", response);
  }

  @Test
  @DisplayName("GET /products/stock search success")
  void search() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(STOCK_BASE_PATH + "/PROfZWswkooMhZ5AL100")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Inventario encontrado en test {}", response);
  }

  @Test
  @DisplayName("GET /products/stock search not exists in stock")
  void search_product_without_stock() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(STOCK_BASE_PATH + "/PROfZWswkooMhZ5AL111")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

  @Order(1)
  @ParameterizedTest
  @DisplayName("POST /products/stock success")
  @CsvSource(value = {"PROfZWswkooMhZ5AL101", "PROfZWswkooMhZ5AL107", "PROfZWswkooMhZ5AL102"})
  void save_success(String product) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(STOCK_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getStockRequest(product))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private StockRequest getStockRequest(String product) {
    StockRequest request = new StockRequest();
    request.setStockMin(1);
    request.setStockMax(100);
    request.setStock(10);
    request.setProductId(product);
    return request;
  }
}
