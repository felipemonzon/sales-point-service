package com.moontech.salespoint.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salespoint.commons.enums.Genre;
import com.moontech.salespoint.configuration.MysqlBaseConfigurationTest;
import com.moontech.salespoint.configuration.TestConstants;
import com.moontech.salespoint.infrastructure.model.request.UserRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * test de las apis de usuarios.
 *
 * @author Felipe Monzón
 * @enterprise moontech
 * @since 01 feb., 2024
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = TestConstants.ROLE_ADMIN)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends MysqlBaseConfigurationTest {
  @Autowired private MockMvc mockMvc;

  /** Mapper. */
  @Autowired private ObjectMapper objectMapper;

  /** Ruta para usuarios. */
  private static final String USERS_BASE_PATH = "/users";

  @Test
  @DisplayName("GET /users success")
  void retrieve_all(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    String token =
        "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmZWxpcGVtb256b24yNzA1IiwiQ0xBSU1fVE9LRU4iOiJST0xFX0FETUlOIiwiaWF0IjoxNzA5MTE2OTA5LCJpc3MiOiJJU1NVRVIiLCJleHAiOjE3MDkxMjY5MDl9.ZovkRbxroc6ripGAWoCsWxKJcXXIkemWKXTTKHA9fcyXP5BxaOSsXJ9CjlU9xN-PulmJ3UUtObBIChxcK5Vw3g";
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(USERS_BASE_PATH)
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                    .header(HttpHeaders.AUTHORIZATION, token))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Usuarios encontrados en test {}", response);
  }

  @Test
  @DisplayName("GET /users/active success")
  void retrieve_active_users() throws Exception {
    String response =
        this.mockMvc
            .perform(
                MockMvcRequestBuilders.get(USERS_BASE_PATH + "/active")
                    .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .getResponse()
            .getContentAsString();
    log.info("Usuarios activos encontrados en test {}", response);
  }

  @Test
  @Order(2)
  @DisplayName("POST /users error")
  void save_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(USERS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getUserRequest("1234567", "felipemonzon1010"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(3)
  @DisplayName("POST /users exists")
  void save_exists(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(USERS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getUserRequest("123456", "felipemonzon2705"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @DisplayName("POST /users exists")
  void save_bad_request(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    UserRequest request = new UserRequest();
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(USERS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  @Order(2)
  @DisplayName("POST /users")
  void save_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post(USERS_BASE_PATH)
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getUserRequest("1234567", "felipemonzon1010"))))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  @Order(4)
  @DisplayName("PUT /users")
  void update_error(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(USERS_BASE_PATH + "/USRScRxIgmINwH4es3pQ")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getUserRequest("1234567", "felipemonzon2705"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  @Order(5)
  @DisplayName("PUT /users")
  void update_success(TestInfo testInfo) throws Exception {
    log.info(TestConstants.TEST_RUNNING, testInfo.getDisplayName());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put(USERS_BASE_PATH + "/USU324htgd243yt567jh")
                .header(TestConstants.UUID_HEADER, String.valueOf(UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    this.objectMapper.writeValueAsString(
                        this.getUserRequest("1234567", "felipemonzon2705"))))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  private UserRequest getUserRequest(String password, String username) {
    UserRequest request = new UserRequest();
    request.setCel("6671568899");
    request.setGenre(Genre.MALE);
    request.setEmail("felipemonzon@gmail.com");
    request.setFirstName("Felipe");
    request.setLastName("monzon");
    request.setUsername(username);
    request.setPassword(password);
    Set<Long> profiles = new HashSet<>();
    profiles.add(1L);
    request.setProfiles(profiles);
    request.setEnterpriseId(1L);
    return request;
  }
}
