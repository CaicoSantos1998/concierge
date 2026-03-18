package github.caicosantos.concierge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "ClientRequest", description = "Information needed to register the new client!")
public record ClientRegisterDTO(
        @NotBlank(message = "This field is required!")
        String clientId,
        @NotBlank(message = "This field is required!")
        String clientSecret,
        @NotBlank(message = "This field is required!")
        String redirectURI,
        String scope
) {
}
