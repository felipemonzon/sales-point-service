package com.moontech.salespoint.application.business;

import com.moontech.salespoint.commons.constant.ApiConstant;
import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.domain.entity.RoleEntity;
import com.moontech.salespoint.domain.entity.UserEntity;
import com.moontech.salespoint.domain.repository.UserRepository;
import com.moontech.salespoint.infrastructure.model.response.SecurityResponse;
import io.micrometer.observation.annotation.Observed;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Inicio de sesión.
 *
 * @author Felipe Monzón
 * @since May 31, 2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginBusiness implements UserDetailsService {
  /** Repositorio de usuarios. */
  private final UserRepository userRepository;

  @Override
  @Transactional
  @Observed(name = "login", contextualName = "login")
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("Proceso de login");
    UserEntity user =
        userRepository
            .findByUsernameAndStatus(username, Status.ACTIVE)
            .orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + username));
    SecurityResponse credential = new ModelMapper().map(this.build(user), SecurityResponse.class);
    credential.setId(user.getId());
    credential.setCel(user.getCel());
    credential.setEmail(user.getEmail());
    credential.setFirstName(user.getFirstName());
    credential.setLastName(user.getLastName());
    credential.setGenre(user.getGenre());
    credential.setStatus(user.getStatus());
    credential.setDisplayName(user.getFirstName() + ApiConstant.WHITE_SPACE + user.getLastName());
    credential.setEnterpriseId(user.getEnterprise().getId());
    credential.setEnterpriseName(user.getEnterprise().getName());
    return credential;
  }

  /**
   * Convierte el objeto {@code UserEntity} a {@code UserDetails}
   *
   * @param user {@code UserEntity}
   * @return {@code UserDetails}
   */
  private UserDetails build(UserEntity user) {
    return new User(user.getUsername(), user.getPassword(), getAuthorities(user));
  }

  /**
   * Obtiene el rol de autorización.
   *
   * @param retrievedUser {@code UserEntity}
   * @return {@code GrantedAuthority}
   */
  private Set<? extends GrantedAuthority> getAuthorities(UserEntity retrievedUser) {
    Set<RoleEntity> roles = retrievedUser.getRoles();
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
    return authorities;
  }
}
