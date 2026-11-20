package mx.unam.fes.zacatlan.user.repository;

import java.util.Optional;

import mx.unam.fes.zacatlan.user.entity.Role;
import mx.unam.fes.zacatlan.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByRole(Role role);
}