package github.caicosantos.concierge.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClientResponse", description = "Detailed client information.")
public record ClientResultSearchDTO(
        String clientId,
        String redirectURI,
        String scope
) {
}
