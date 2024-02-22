package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.commons.enums.Genre;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.CustomerRequest;
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
 * Test de las apis de Clientes.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 20 feb., 2024
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta para clientes. */
  private static final String CUSTOMERS_BASE_PATH = "/customers";

  @Test
  @DisplayName("GET /customers success")
  void retrieve_all() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(CUSTOMERS_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Clientes encontrados en test {}", response);
  }

  @ParameterizedTest
  @DisplayName("POST /customers")
  @CsvSource(value = {"felipe, monzon", "felipe, lopez"})
  void save_parametrized(String name, String lastname) throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(CUSTOMERS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(this.getCustomerRequest(name, lastname))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @DisplayName("POST /customers")
  void save_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(CUSTOMERS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getCustomerRequest("name", "lastname"))))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  @DisplayName("POST /customers bad request")
  void save_bad_request(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(CUSTOMERS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(new CustomerRequest())))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  @Order(3)
  @DisplayName("PUT /customers error")
  void update_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(CUSTOMERS_BASE_PATH + "/USRScRxIgmINwH4es3pQ")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(this.getCustomerRequest("juan", "lopez"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(4)
  @DisplayName("PUT /customers success")
  void update_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(CUSTOMERS_BASE_PATH + "/CUSXXXXXXXXXXXXXXXX1")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getCustomerRequest("felipe", "monzon"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private CustomerRequest getCustomerRequest(String firstname, String lastname) {
    CustomerRequest request = new CustomerRequest();
    request.setCel("6671568899");
    request.setGenre(Genre.MALE);
    request.setEmail("felipemonzon@gmail.com");
    request.setFirstName(firstname);
    request.setLastName(lastname);
    return request;
  }
}
