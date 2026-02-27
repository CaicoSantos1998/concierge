package github.caicosantos.concierge.controller.dto;

import java.util.List;

public record UserRegisterDTO(
        String login,
        String password,
        List<String> roles
) {
}
