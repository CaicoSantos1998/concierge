package github.caicosantos.concierge.repository;

import github.caicosantos.concierge.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);

    List<User> findAll(Specification<User> spec);
}
