package github.caicosantos.concierge.service;

import github.caicosantos.concierge.exceptions.DuplicateRegisterException;
import github.caicosantos.concierge.exceptions.SearchCombinationNotFoundException;
import github.caicosantos.concierge.model.User;
import github.caicosantos.concierge.repository.UserRepository;
import github.caicosantos.concierge.repository.specs.UserSpecs;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void save(User user) {
        repository.findByLogin(user.getLogin()).ifPresent(userLogin -> {
            throw new DuplicateRegisterException("The login already exists in the system!");
        });
        user.setPassword(encoder.encode(user.getPassword()));
        if(user.getRoles()==null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("USER"));
        }
        repository.save(user);
    }

    public Optional<User> getByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<User> getById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public User update(User user) {
        if(!repository.existsById(user.getId())) {
            throw new EntityNotFoundException("To update, the User must the exist in the database.");
        }
        return repository.save(user);
    }

    public List<User> search(String login, String roles) {
        Specification<User> spec = Specification.where((root, cq, cb) -> cb.conjunction());
        if(login!=null && !login.isBlank()) {
            spec = spec.and(UserSpecs.loginLike(login));
        }
        if(roles!=null && !roles.isBlank()) {
            spec = spec.and(UserSpecs.rolesLike(roles));
        }
        List<User> list = repository.findAll(spec);
        if(list.isEmpty()) {
            throw new SearchCombinationNotFoundException("There is no user with that combination!");
        }
        return list;
    }
}
