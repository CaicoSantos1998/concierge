package github.caicosantos.concierge.dto;

public record ClientResultSearchDTO(
        String clientId,
        String redirectURI,
        String scope
) {
}
