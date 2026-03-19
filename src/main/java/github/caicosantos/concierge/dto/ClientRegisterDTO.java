package github.caicosantos.concierge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "ClientRequest", description = "Information needed to register the new client!")
public record ClientRegisterDTO(
        @NotBlank(message = "This field is required!")
        @Size(min = 4, max = 20)
        String clientId,
        @NotBlank(message = "This field is required!")
        String clientSecret,
        @NotBlank(message = "This field is required!")
        String redirectURI,
        String scope
) {
}
