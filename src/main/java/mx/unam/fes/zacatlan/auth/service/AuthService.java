package mx.unam.fes.zacatlan.auth.service;

import lombok.RequiredArgsConstructor;
import mx.unam.fes.zacatlan.auth.dto.AuthResponseDTO;
import mx.unam.fes.zacatlan.auth.dto.LoginRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String token = jwtService.generateToken(authentication.getName(), role);

        return new AuthResponseDTO(token, "Bearer", authentication.getName(), role);
    }
}