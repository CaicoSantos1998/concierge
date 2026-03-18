package github.caicosantos.concierge.dto;

import java.util.List;

public record UserResultSearchDTO(
        String login,
        List<String> roles
) {
}
