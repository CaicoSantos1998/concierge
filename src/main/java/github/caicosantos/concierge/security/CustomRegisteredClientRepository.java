package github.caicosantos.concierge.security;

import github.caicosantos.concierge.model.Client;
import github.caicosantos.concierge.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {
    private final ClientService service;
    private final TokenSettings token;
    private final ClientSettings settings;

    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        return service
                .getById(UUID.fromString(id))
                .map(this::mapToRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return service
                .getByClientId(clientId)
                .map(this::mapToRegisteredClient)
                .orElse(null);
    }

    private RegisteredClient mapToRegisteredClient(Client client) {
        return RegisteredClient
                .withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .redirectUri(client.getRedirectURI())
                .scope(client.getScope())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenSettings(token)
                .clientSettings(settings)
                .build();
    }
}
