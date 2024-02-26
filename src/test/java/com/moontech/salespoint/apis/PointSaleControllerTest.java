package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.ClosePointSaleRequest;
import com.moontech.salespoint.infrastructure.model.request.OpenPointSaleRequest;
import com.moontech.salespoint.infrastructure.model.request.PointSaleRequest;
import com.moontech.salespoint.infrastructure.model.request.WithdrawalPointSaleRequest;
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

/**
 * Test de las apis de punto de venta.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 24 feb., 2024
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PointSaleControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta base de punto de venta. */
  private static final String POS_BASE_PATH = "/pointSales";

  @Test
  @DisplayName("GET /pointSales success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(POS_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Puntos de venta encontrados en test {}", response);
  }

  @Test
  @DisplayName("GET /pointSales search")
  void search() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(POS_BASE_PATH + "/caja")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Puntos de venta encontrados en test {}", response);
  }

  @Order(1)
  @ParameterizedTest
  @DisplayName("POST /pointSales success")
  @CsvSource(value = {"Caja 1", "Caja 1"})
  void save_success(String name) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getPointSaleRequest(name))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(2)
  @DisplayName("POST /pointSales")
  void save_duplicate(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getPointSaleRequest("caja"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(3)
  @Sql(scripts = {"/point-sale-script-test.sql"})
  @DisplayName("PUT /pointSales success")
  void update_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(POS_BASE_PATH + "/POSLCwJDF7Uq2wY9HbI1")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getPointSaleRequest("Caja 1"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(4)
  @DisplayName("PUT /pointSales success")
  void update_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(POS_BASE_PATH + "/POSLCwJDF7Uq2wY9Hb22")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.getPointSaleRequest("Caja 1"))))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  @Order(5)
  @DisplayName("DELETE /pointSales success")
  void delete_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(POS_BASE_PATH + "/POSLCwJDF7Uq2wY9HbI1")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(6)
  @DisplayName("POST /pointSales/open open")
  void open_point_sale(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH + "/open")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getOpenPointSaleRequest(
                            "POSLCwJDF7Uq2wY9HbI1", "USU324htgd243yt56100"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(7)
  @DisplayName("POST /pointSales/open started")
  void open_point_sale_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH + "/open")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getOpenPointSaleRequest(
                            "POSLCwJDF7Uq2wY9HbI1", "USU324htgd243yt56101"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Order(8)
  @ParameterizedTest
  @DisplayName("POST /pointSales/open error")
  @CsvSource(
      value = {
        "USU324htgd243yt56100, POSLCwJDF7Uq2wY9Hb100",
        "USU324htgd243yt56101, POSLCwJDF7Uq2wY9HbI1"
      })
  void open_point_sale_user_and_point_sale_not_register(String user, String pointSale)
      throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH + "/open")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getOpenPointSaleRequest(user, pointSale))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Order(9)
  @ParameterizedTest
  @DisplayName("POST /pointSales/close error")
  @CsvSource(
      value = {
        "USU324htgd243yt56100, POSLCwJDF7Uq2wY9Hb11",
        "USU324htgd243yt56101, POSLCwJDF7Uq2wY9Hb10"
      })
  void close_point_sale_user_and_point_sale_not_register(String user, String pointSale)
      throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH + "/close")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getClosePointSaleRequest(pointSale, user))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(10)
  @DisplayName("POST /pointSales/close error")
  void close_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH + "/close")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getClosePointSaleRequest(
                            "POSLCwJDF7Uq2wY9Hb10", "USU324htgd243yt56100"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Order(11)
  @ParameterizedTest
  @DisplayName("POST /pointSales/withdrawal")
  @CsvSource(value = {"POSLCwJDF7Uq2wY9HbI1", "POSLCwJDF7Uq2wY9Hb10"})
  void withdrawal_point_sale_not_opened(String pointSale) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(POS_BASE_PATH + "/withdrawal")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getWithdrawalPointSaleRequest(pointSale))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(12)
  @DisplayName("POST /pointSales/withdrawal success")
  void withdrawal_update_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(POS_BASE_PATH + "/withdrawal/WIDGoKXQTY1Gp8N4Bxl7")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getWithdrawalPointSaleRequest("POSLCwJDF7Uq2wY9HbI1"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(13)
  @DisplayName("POST /pointSales/withdrawal close pos")
  void withdrawal_update_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(POS_BASE_PATH + "/withdrawal/WIDGoKXQTY1Gp8N4Bx11")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getWithdrawalPointSaleRequest("POSLCwJDF7Uq2wY9Hb10"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(14)
  @DisplayName("POST /pointSales/withdrawal invoice not exists")
  void withdrawal_update_not_onvoice(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(POS_BASE_PATH + "/withdrawal/WIDGoKXQTY1Gp8N4Bx11")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getWithdrawalPointSaleRequest("POSLCwJDF7Uq2wY9HbI1"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private PointSaleRequest getPointSaleRequest(String name) {
    PointSaleRequest request = new PointSaleRequest();
    request.setName(name);
    request.setStatus(Status.ACTIVE);
    request.setEnterpriseId(1L);
    return request;
  }

  private OpenPointSaleRequest getOpenPointSaleRequest(String pointSale, String userID) {
    OpenPointSaleRequest request = new OpenPointSaleRequest();
    request.setPointSale(pointSale);
    request.setOpenUser(userID);
    request.setOpenAmount(BigDecimal.ONE);
    return request;
  }

  private ClosePointSaleRequest getClosePointSaleRequest(String pointSale, String user) {
    ClosePointSaleRequest request = new ClosePointSaleRequest();
    request.setPointSale(pointSale);
    request.setCloseUser(user);
    return request;
  }

  private WithdrawalPointSaleRequest getWithdrawalPointSaleRequest(String pointSale) {
    WithdrawalPointSaleRequest request = new WithdrawalPointSaleRequest();
    request.setDescription("coca cola");
    request.setAmount(BigDecimal.ONE);
    request.setPointSaleId(pointSale);
    return request;
  }
}
