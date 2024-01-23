package com.moontech.salesPoint.infrastructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moontech.salesPoint.commons.constant.ApiConstant;
import com.moontech.salesPoint.commons.constant.ErrorConstant;
import com.moontech.salesPoint.infrastructure.exception.custom.ErrorResponse;
import com.moontech.salesPoint.infrastructure.exception.management.ExceptionManagement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final HttpStatus statusErrorResponse;

  public JwtAuthenticationFailureHandler(HttpStatus statusErrorResponse) {
    this.statusErrorResponse = statusErrorResponse;
  }

  public JwtAuthenticationFailureHandler() {
    this.statusErrorResponse = HttpStatus.UNAUTHORIZED;
  }

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    response.setStatus(this.statusErrorResponse.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response
        .getWriter()
        .append(
            new ObjectMapper()
                .writeValueAsString(
                    ErrorResponse.builder()
                        .type(ExceptionManagement.ErrorType.INVALID.name())
                        .code(ErrorConstant.INVALID_CREDENTIAL_USER_CODE)
                        .message(ErrorConstant.INVALID_CREDENTIAL_USER_MESSAGE)
                        .uuid(request.getHeader(ApiConstant.HEADER_UUID))
                        .build()));
  }
}
