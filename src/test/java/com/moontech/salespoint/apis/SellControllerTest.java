package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.SellDetailRequest;
import com.moontech.salespoint.infrastructure.model.request.SellRequest;
import java.math.BigDecimal;
import java.util.Collections;
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
 * Test de las apis de ventas.
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
@Sql(scripts = {"/sell-script-test.sql"})
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SellControllerTest extends MysqlBaseConfigurationTest {
  /** MockMvc. */
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta base de ventas. */
  private static final String SELL_BASE_PATH = "/sells";

  @Test
  @DisplayName("GET /sells success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(SELL_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Ventas encontrados en test {}", response);
  }

  @Test
  @DisplayName("GET /sells search success")
  void search() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(SELL_BASE_PATH + "/TXNpKLGQL6JyUOXlQIIH")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Ventas encontrado en test {}", response);
  }

  @Test
  @DisplayName("GET /sells search date and status success")
  void search_by_date_and_status() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(SELL_BASE_PATH + "/PAYMENT/2024-02-28")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Ventas encontrado en test {}", response);
  }

  @Order(1)
  @ParameterizedTest
  @DisplayName("POST /sells success")
  @CsvSource(
      value = {
        "PROfZWswkooMhZ5AL602, 1, 1",
        "PROfZWswkooMhZ5AL601, 1, 1",
        "PROfZWswkooMhZ5AL601, 1, 10",
        "PROfZWswkooMhZ5AL102, 10, 1"
      })
  void save_success(String product, long method, int piece) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(SELL_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getSellRequest(product, method, piece))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private SellRequest getSellRequest(String product, long method, int piece) {
    SellRequest request = new SellRequest();
    request.setStatus(Status.BANK_ROLL);
    request.setTotal(new BigDecimal("100"));
    request.setInvoice(null);
    request.setPointSaleId("POSLCwJDF7Uq2wY9H600");
    request.setMethodPaymentId(method);
    request.setCustomerId("CUSXXXXXXXXXXXXXXXX1");
    request.setDetails(Collections.singletonList(this.getSellDetailRequest(product, piece)));
    return request;
  }

  private SellDetailRequest getSellDetailRequest(String product, int piece) {
    SellDetailRequest request = new SellDetailRequest();
    request.setPiece(piece);
    request.setProductId(product);
    return request;
  }
}
