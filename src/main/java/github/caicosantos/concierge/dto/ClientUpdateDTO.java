package github.caicosantos.concierge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "UserRequest", description = "Information needed to update the user!")
public record ClientUpdateDTO(
        @NotBlank(message = "This field is required!")
        String redirectURI,
        @NotBlank(message = "This field is required!")
        String scope
) {
}
