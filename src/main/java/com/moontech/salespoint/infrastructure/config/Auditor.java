package com.moontech.salespoint.infrastructure.config;

import com.moontech.salespoint.commons.constant.ApiConstant;
import com.moontech.salespoint.infrastructure.model.response.SecurityResponse;
import java.util.Objects;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Configuración de la clase auditora.
 *
 * @author Felipe Monzón
 * @since May 26, 2023
 */
@Component
public class Auditor implements AuditorAware<String> {
  /**
   * Consulta el usuario auditor.
   *
   * @return usuario encontrado
   */
  @Override
  public Optional<String> getCurrentAuditor() {
    String user = ApiConstant.USER_SYSTEM;
    if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
      SecurityResponse login =
          new ModelMapper()
              .map(
                  SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                  SecurityResponse.class);
      if (!ObjectUtils.isEmpty(login.getUsername())) {
        user = login.getUsername();
      }
    }
    return Optional.of(user);
  }
}
