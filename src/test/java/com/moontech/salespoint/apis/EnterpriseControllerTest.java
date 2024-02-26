package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.EnterpriseRequest;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
 * Test de las apis de empresas.
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
class EnterpriseControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta base de empresas. */
  private static final String ENTERPRISE_BASE_PATH = "/enterprises";

  @Test
  @DisplayName("GET /enterprises success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(ENTERPRISE_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Empresas encontrados en test {}", response);
  }

  @Test
  @Order(1)
  @DisplayName("POST /enterprises success")
  void save_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(ENTERPRISE_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getEnterpriseRequest("ASD462299Q21", "Juana lopes"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(2)
  @DisplayName("POST /enterprises exists")
  void save_exists(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(ENTERPRISE_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getEnterpriseRequest("ASD462299Q22", "Juan lopes"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @DisplayName("POST /enterprises exists")
  void save_bad_request(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(ENTERPRISE_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new EnterpriseRequest())))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  @Order(2)
  @DisplayName("POST /enterprises")
  void save_duplicate(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(ENTERPRISE_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getEnterpriseRequest("ASD462299Q23", "Juan lopes"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(5)
  @DisplayName("PUT /enterprises error")
  void update_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(ENTERPRISE_BASE_PATH + "/SUP12345678902134573")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getEnterpriseRequest("ASD462299Q22", "Juan lopes"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(7)
  @Sql(scripts = {"/enterprise-script.sql"})
  @DisplayName("PUT /enterprises success")
  void update_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(ENTERPRISE_BASE_PATH + "/ENT6734htdge362xnhd4")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getEnterpriseRequest("JUJI877654HSFH12", "Felipao lopez"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(8)
  @DisplayName("DELETE /enterprises not-found")
  void delete_not_found(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(ENTERPRISE_BASE_PATH + "/ENT6734htdge362x1234")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(9)
  @DisplayName("DELETE /enterprises success")
  void delete_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete(ENTERPRISE_BASE_PATH + "/ENT6734htdge362xnhd4")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private EnterpriseRequest getEnterpriseRequest(String rfc, String name) {
    EnterpriseRequest request = new EnterpriseRequest();
    request.setPhone("6671568899");
    request.setAddress("muy lejos");
    request.setRfc(rfc);
    request.setActive(Boolean.TRUE);
    request.setName(name);
    request.setManager("Juan perez osos");
    return request;
  }
}
