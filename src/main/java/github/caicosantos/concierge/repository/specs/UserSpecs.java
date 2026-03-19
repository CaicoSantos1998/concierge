package github.caicosantos.concierge.repository.specs;

import github.caicosantos.concierge.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {
    public static Specification<User> loginLike(String login) {
        return (root, cq, cb) ->
                cb.like(cb.upper(root.get("login")), "%" + login.toUpperCase() + "%");
    }

    public static Specification<User> rolesLike(String roles) {
        return (root, cq, cb) ->
                cb.like(cb.upper(root.get("roles")), "%" + roles.toUpperCase() + "%");
    }
}
