package com.moontech.salespoint.domain.repository;

import com.moontech.salespoint.commons.enums.Status;
import com.moontech.salespoint.domain.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de usuarios.
 *
 * @author Felipe Monzón
 * @since May 31, 2023
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  /**
   * Consulta un usuario por su nombre de usuario y estatus.
   *
   * @param username nombre de usuario
   * @param status status del usuario
   * @return usuario encontrado o vacío sino se encontró nada
   */
  Optional<UserEntity> findByUsernameAndStatus(String username, Status status);

  /**
   * Consulta un usuario por su nombre de usuario.
   *
   * @param username nombre de usuario
   * @return usuario encontrado o vacío sino se encontró nada
   */
  Optional<UserEntity> findByUsername(String username);

  /**
   * Consulta usuarios por su status.
   *
   * @param pageable paginación
   * @param status estatus del usuario
   * @return lista de usuarios encontrados
   */
  List<UserEntity> findAllByStatus(Pageable pageable, Status status);

  /**
   * Consulta un usuario por su identificador.
   *
   * @param idUser identificador del usuario
   * @return datos del usuario encontrado
   */
  UserEntity findByIdUser(String idUser);
}
