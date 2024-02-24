package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.DebtRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
 * Test de las apis de Deudas de los clientes.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 23 feb., 2024
 */
@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/debt-script.sql"})
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DebtControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta para deudas de los clientes. */
  private static final String DEBT_BASE_PATH = "/debts";

  @Test
  @Order(5)
  @DisplayName("GET /debts success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(DEBT_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Deudas encontrados en test {}", response);
  }

  @Test
  @Order(2)
  @DisplayName("GET /debts/customer success")
  void retrieve_all_customer() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(DEBT_BASE_PATH + "/customer/CUSXXXXXXXXXXXXXXXX1")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Deudas del cliente encontrados en test {}", response);
  }

  @Test
  @Order(3)
  @DisplayName("GET /debts/customer active")
  void retrieve_all_customer_active() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(DEBT_BASE_PATH + "/customer/CUSXXXXXXXXXXXXXXXX1/ACTIVE")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Deudas activas del cliente encontrados en test {}", response);
  }

  @Test
  @Order(4)
  @DisplayName("GET /debts/customer/canceled success")
  void retrieve_all_customer_canceled() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(
                        DEBT_BASE_PATH + "/customer/CUSXXXXXXXXXXXXXXXX1/CANCELED")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Deudas canceladas del cliente encontrados en test {}", response);
  }

  @Order(1)
  @ParameterizedTest
  @DisplayName("POST /debts")
  @CsvSource(value = {"test 1", "test 2"})
  void save_parametrized(String name) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(DEBT_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getDebtRequest())))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @DisplayName("POST /debts")
  void save_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(DEBT_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getDebtRequest())))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @DisplayName("POST /debts bad request")
  void save_bad_request(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(DEBT_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new DebtRequest())))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  @Order(6)
  @DisplayName("PUT /debts error")
  void update_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(DEBT_BASE_PATH + "/DEBFbLLRo3N0SLOe8CI3")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getDebtRequest())))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(7)
  @DisplayName("PUT /debts success")
  void update_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(DEBT_BASE_PATH + "/DEBFbLLRo3N0SLOe8CIT")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getDebtRequest())))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(8)
  @DisplayName("DELETE /debts success")
  void delete_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(DEBT_BASE_PATH + "/DEBFbLLRo3N0SLOe8CIT")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(11)
  @DisplayName("DELETE /debts not exists")
  void delete_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(DEBT_BASE_PATH + "/DEBFbLLRo3N0SLOe8CIS")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(9)
  @DisplayName("PUT /debts error")
  void update_message_not_readable(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(DEBT_BASE_PATH + "/DEBFbLLRo3N0SLOe8CIT")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is5xxServerError());
  }

  @Test
  @Order(10)
  @DisplayName("method not supported")
  void method_not_supported(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.patch(DEBT_BASE_PATH + "/DEBFbLLRo3N0SLOe8CIT")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  private DebtRequest getDebtRequest() {
    DebtRequest request = new DebtRequest();
    request.setDebtDate(LocalDateTime.now());
    request.setStatus(Status.ACTIVE);
    request.setTotal(BigDecimal.ONE);
    request.setCustomerId("CUSXXXXXXXXXXXXXXXX1");
    return request;
  }
}
