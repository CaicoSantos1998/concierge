package github.caicosantos.concierge.service;

import github.caicosantos.concierge.exceptions.DuplicateRegisterException;
import github.caicosantos.concierge.model.User;
import github.caicosantos.concierge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
