package github.caicosantos.concierge.controller;

import github.caicosantos.concierge.controller.dto.UserRegisterDTO;
import github.caicosantos.concierge.controller.mappers.UserMapper;
import github.caicosantos.concierge.model.User;
import github.caicosantos.concierge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GeneratorHeaderLocationController{
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> save(UserRegisterDTO dto) {
        User user = mapper.toEntity(dto);
        service.save(user);
        URI location = generateHeaderLocation(user.getId());
        return ResponseEntity
                .created(location)
                .build();
    }

}
