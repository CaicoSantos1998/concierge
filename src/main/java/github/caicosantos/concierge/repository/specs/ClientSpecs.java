package github.caicosantos.concierge.repository.specs;

import github.caicosantos.concierge.model.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecs {

    public static Specification<Client> clientIdLike(String clientId) {
        return (root, cq, cb) ->
                cb.like(cb.upper(root.get("clientId")), "%" + clientId.toUpperCase() + "%");
    }

    public static Specification<Client> scopeLike(String scope) {
        return (root, cq, cb) ->
                cb.like(cb.upper(root.get("scope")), "%" + scope.toUpperCase() + "%");
    }
}
