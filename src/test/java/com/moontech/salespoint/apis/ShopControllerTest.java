package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.DetailRequest;
import com.moontech.salespoint.infrastructure.model.request.SellRequest;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import com.moontech.salespoint.infrastructure.model.request.ShopRequest;
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
 * Test de las apis de compras.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 29 feb., 2024
 */
@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/shop-script-test.sql"})
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShopControllerTest extends MysqlBaseConfigurationTest {
  /** MockMvc. */
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta base de compras. */
  private static final String SHOP_BASE_PATH = "/shops";

  @Test
  @DisplayName("GET /shops success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(SHOP_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Compras encontrados en test {}", response);
  }

  @Test
  @DisplayName("GET /shops search success")
  void search() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(SHOP_BASE_PATH + "/TXNB6MH1F5zFzzx9HD4d")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Compras encontrado en test {}", response);
  }

  @Test
  @DisplayName("GET /shops search date and status success")
  void search_by_date_and_status() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(SHOP_BASE_PATH + "/PAYMENT/2024-02-29")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Compras encontrado en test {}", response);
  }

  @Order(1)
  @ParameterizedTest
  @DisplayName("POST /shops success")
  @CsvSource(
      value = {
        "PROfZWswkooMhZ5AL701, 1, 1",
        "PROfZWswkooMhZ5AL701, 1, 1",
        "PROfZWswkooMhZ5AL700, 1, 11",
        "PROfZWswkooMhZ5AL702, 10, 1"
      })
  void save_success(String product, long method, int piece) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(SHOP_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getShopRequest(product, method, piece))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private ShopRequest getShopRequest(String product, long method, int piece) {
    ShopRequest request = new ShopRequest();
    request.setStatus(Status.PAYMENT);
    request.setTotal(new BigDecimal("100"));
    request.setInvoice(null);
    request.setPointSaleId("POSLCwJDF7Uq2wY9H700");
    request.setMethodPaymentId(method);
    request.setSupplierId("SUPkkI4KwH8ykQfq2700");
    request.setDetails(Collections.singletonList(this.getSellDetailRequest(product, piece)));
    return request;
  }

  private DetailRequest getSellDetailRequest(String product, int piece) {
    DetailRequest request = new DetailRequest();
    request.setPiece(piece);
    request.setProductId(product);
    return request;
  }
}
