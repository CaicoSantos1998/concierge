package github.caicosantos.concierge.service;

import github.caicosantos.concierge.model.Client;
import github.caicosantos.concierge.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public void save(Client client) {
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        repository.save(client);
    }

    public Optional<Client> getById(UUID id) {
        return repository.findById(id);
    }

    public Optional<Client> getByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
