package github.caicosantos.concierge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(name = "UserRequest", description = "Information needed to update the user!")
public record UserUpdateDTO(
        @NotBlank(message = "This field is required!")
        @Size(min = 4, max = 20)
        String login,
        @NotBlank(message = "This field is required!")
        List<String> roles
) {
}
