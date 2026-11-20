package mx.unam.fes.zacatlan.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.unam.fes.zacatlan.auth.dto.AuthResponseDTO;
import mx.unam.fes.zacatlan.auth.dto.LoginRequestDTO;
import mx.unam.fes.zacatlan.auth.service.AuthService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .body(authService.login(request));
    }
}