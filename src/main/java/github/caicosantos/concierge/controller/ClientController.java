package github.caicosantos.concierge.controller;

import github.caicosantos.concierge.controller.dto.ClientRegisterDTO;
import github.caicosantos.concierge.controller.mappers.ClientMapper;
import github.caicosantos.concierge.model.Client;
import github.caicosantos.concierge.repository.ClientRepository;
import github.caicosantos.concierge.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController implements GeneratorHeaderLocationController {
    private final ClientService service;
    private final ClientRepository repository;
    private final ClientMapper mapper;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    public ResponseEntity<Void> save(@RequestBody @Valid ClientRegisterDTO dto) {
        Client client = mapper.toEntity(dto);
        service.save(client);
        return ResponseEntity
                .created(generateHeaderLocation(client.getId()))
                .build();
    }
}
