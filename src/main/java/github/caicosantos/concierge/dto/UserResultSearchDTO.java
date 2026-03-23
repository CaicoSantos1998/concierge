package github.caicosantos.concierge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "UserResponse", description = "Detailed user information.")
public record UserResultSearchDTO(
        String login,
        String email,
        List<String> roles
) {
}
