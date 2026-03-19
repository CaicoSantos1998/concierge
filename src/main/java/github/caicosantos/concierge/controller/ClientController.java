package github.caicosantos.concierge.controller;

import github.caicosantos.concierge.configuration.ApiStandardErrors;
import github.caicosantos.concierge.dto.ClientRegisterDTO;
import github.caicosantos.concierge.dto.ClientResultSearchDTO;
import github.caicosantos.concierge.dto.ClientUpdateDTO;
import github.caicosantos.concierge.mappers.ClientMapper;
import github.caicosantos.concierge.model.Client;
import github.caicosantos.concierge.service.ClientService;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
public class ClientController implements GeneratorHeaderLocationController {
    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasAuthority('SCOPE_MANAGER')")
    @Operation(summary = "Save", description = "Register the new client!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Record created successfully!"),
    })
    public ResponseEntity<Void> save(@RequestBody @Valid ClientRegisterDTO dto) {
        Client client = mapper.toEntity(dto);
        service.save(client);
        URI location = generateHeaderLocation(client.getId());
        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get By ID", description = "Find a specific client using its unique ID!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request completed successfully!")
    })
    public ResponseEntity<ClientResultSearchDTO> getById(@PathVariable UUID id) {
        return service
                .getById(id)
                .map(existID -> {
                    ClientResultSearchDTO dto = mapper.toDTO(existID);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasAuthority('SCOPE_MANAGER')")
    @Operation(summary = "Delete", description = "Deleting a specific client using its unique ID!")
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
    @Operation(summary = "Update", description = "Update a specific client using its unique ID!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Record updated successfully!")
    })
    public ResponseEntity<Object> update(
            @RequestBody @Valid ClientUpdateDTO dto,
            @PathVariable UUID id) {
        return service
                .getById(id)
                .map(thisClientExist -> {
                    thisClientExist.setRedirectURI(dto.redirectURI());
                    thisClientExist.setScope(dto.scope());
                    service.update(thisClientExist);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Search", description = "Perform clients searches using parameters!")
    @ApiStandardErrors
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request completed successfully!")
    })
    public ResponseEntity<List<ClientResultSearchDTO>> getAllClients(
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) String scope) {
        List<Client> resultSearch = service.search(clientId, scope);
        List<ClientResultSearchDTO> clientList = resultSearch
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(clientList);
    }
}
