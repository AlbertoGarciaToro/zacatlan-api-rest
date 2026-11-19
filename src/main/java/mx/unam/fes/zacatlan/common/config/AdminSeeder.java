package mx.unam.fes.zacatlan.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.unam.fes.zacatlan.user.entity.Role;
import mx.unam.fes.zacatlan.user.entity.UserEntity;
import mx.unam.fes.zacatlan.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.admin.username:}")
    private String adminUsername;

    @Value("${app.seed.admin.password:}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByRole(Role.ROLE_ADMIN)) {
            return;
        }
        if (adminUsername.isBlank() || adminPassword.isBlank()) {
            log.warn("No admin exists and seed credentials are not configured");
            return;
        }
        userRepository.save(UserEntity.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ROLE_ADMIN)
                .build());
        log.info("Initial admin '{}' created", adminUsername);
    }
}