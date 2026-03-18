package github.caicosantos.concierge.service;

import github.caicosantos.concierge.exceptions.SearchCombinationNotFoundException;
import github.caicosantos.concierge.model.Client;
import github.caicosantos.concierge.repository.ClientRepository;
import github.caicosantos.concierge.repository.specs.ClientSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public void save(Client client) {
        client.setClientSecret(encoder.encode(client.getClientSecret()));
        clientRepository.save(client);
    }

    public Optional<Client> getById(UUID id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    public void deleteById(UUID id) {
        clientRepository.deleteById(id);
    }

    public Client update(Client client) {
        if(client.getId()!=null) {
            return clientRepository.save(client);
        }
        throw new IllegalArgumentException("To update, the Client must the exist in the database.");
    }

    public List<Client> search(String clientId, String scope) {
        Specification<Client> spec = Specification.where((root, cq, cb) -> cb.conjunction());
        if(clientId!=null && !clientId.isBlank()) {
            spec = spec.and(ClientSpecs.clientIdLike(clientId));
        }
        if(scope!=null && !scope.isBlank()) {
            spec = spec.and(ClientSpecs.scopeLike(scope));
        }
        List<Client> list = clientRepository.findAll(spec);
        if(list.isEmpty()) {
            throw new SearchCombinationNotFoundException("There is no client with that combination!");
        }
        return list;
    }
}
