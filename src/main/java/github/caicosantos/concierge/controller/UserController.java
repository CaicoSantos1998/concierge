package github.caicosantos.concierge.controller;

import github.caicosantos.concierge.configuration.ApiStandardErrors;
import github.caicosantos.concierge.dto.UserRegisterDTO;
import github.caicosantos.concierge.dto.UserResultSearchDTO;
import github.caicosantos.concierge.dto.UserUpdateDTO;
import github.caicosantos.concierge.mappers.UserMapper;
import github.caicosantos.concierge.model.User;
import github.caicosantos.concierge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController implements GeneratorHeaderLocationController{
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @Operation(summary = "Save", description = "Register the new user!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Record created successfully!")
    })
    public ResponseEntity<Void> save(@RequestBody UserRegisterDTO dto) {
        User user = mapper.toEntity(dto);
        service.save(user);
        URI location = generateHeaderLocation(user.getId());
        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get By ID", description = "Find a specific user using its unique ID!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request completed successfully!")
    })
    public ResponseEntity<UserResultSearchDTO> getById(@PathVariable UUID id) {
        return service
                .getById(id)
                .map(thisIdExist -> {
                    UserResultSearchDTO dto = mapper.toDTO(thisIdExist);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasAuthority('SCOPE_MANAGER')")
    @Operation(summary = "Delete", description = "Deleting a specific user using its unique ID!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Record removed successfully!")
    })
    public ResponseEntity<Object> deleteById(@PathVariable UUID id) {
        return service
                .getById(id)
                .map(thisIdExist -> {
                    service.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasAuthority('SCOPE_MANAGER')")
    @Operation(summary = "Update", description = "Update a specific user using its unique ID!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Record updated successfully!")
    })
    public ResponseEntity<Object> update(
            @RequestBody @Valid UserUpdateDTO dto,
            @PathVariable UUID id) {
        return service
                .getById(id)
                .map(thisUserExist -> {
                    thisUserExist.setLogin(dto.login());
                    thisUserExist.setRoles(dto.roles());
                    service.update(thisUserExist);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
