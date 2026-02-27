package github.caicosantos.concierge.controller.dto;

import jakarta.validation.constraints.NotBlank;

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
