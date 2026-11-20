package mx.unam.fes.zacatlan.auth.dto;

public record AuthResponseDTO(
        String token,
        String tokenType,
        String username,
        String role
) {}