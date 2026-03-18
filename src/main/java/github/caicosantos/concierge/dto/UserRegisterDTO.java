package github.caicosantos.concierge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(name = "UserRequest", description = "Information needed to register the new user!")
public record UserRegisterDTO(
        String login,
        String password,
        List<String> roles
) {
}
