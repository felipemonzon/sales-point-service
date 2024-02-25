package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.PaymentRequest;
import java.math.BigDecimal;
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
 * Test de las apis de pago.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 24 feb., 2024
 */
@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/payment-script-test.sql"})
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta base de pago. */
  private static final String PAYMENT_BASE_PATH = "/payments";

  @Test
  @DisplayName("GET /payment success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(PAYMENT_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Pagos encontrados en test {}", response);
  }

  @Test
  @DisplayName("GET /payment search")
  void search() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(PAYMENT_BASE_PATH + "/PAYaBbLTbo0W2xAZWNMP")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Pagos encontrados en test {}", response);
  }

  @Order(1)
  @ParameterizedTest
  @DisplayName("POST /payment success")
  @CsvSource(value = {"DEBFbLLRo3N0SLOe8100", "DEBFbLLRo3N0SLOe8101"})
  void save_success(String debt) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(PAYMENT_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getPaymentRequest(debt, 1L))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(2)
  @DisplayName("POST /payment method payment not exists")
  void save_method_not_exist(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(PAYMENT_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getPaymentRequest("DEBFbLLRo3N0SLOe8100", 10L))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private PaymentRequest getPaymentRequest(String debtId, long method) {
    PaymentRequest request = new PaymentRequest();
    request.setAmount(BigDecimal.ONE);
    request.setMethodPayment(method);
    request.setDebtId(debtId);
    return request;
  }
}
