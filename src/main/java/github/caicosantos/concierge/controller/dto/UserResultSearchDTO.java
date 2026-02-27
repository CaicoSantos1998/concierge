package github.caicosantos.concierge.controller.dto;

import java.util.List;

public record UserResultSearchDTO(
        String login,
        List<String> roles
) {
}
