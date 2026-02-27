package github.caicosantos.concierge.controller.dto;

public record ClientResultSearchDTO(
        String clientId,
        String redirectURI,
        String scope
) {
}
