package github.caicosantos.concierge.configuration;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Some information is missing or incorrect!"),
        @ApiResponse(responseCode = "401", description = "Session expired or invalid token. Please log in again!"),
        @ApiResponse(responseCode = "403", description = "You don't have permission to perform this action!"),
        @ApiResponse(responseCode = "409", description = "Conflict: This record is linked to other data or already exists."),
        @ApiResponse(responseCode = "422", description = "Please check the information provided!"),
        @ApiResponse(responseCode = "500", description = "Our system encountered an unexpected error. Please try again later!")
})
public @interface ApiStandardErrors {
}
