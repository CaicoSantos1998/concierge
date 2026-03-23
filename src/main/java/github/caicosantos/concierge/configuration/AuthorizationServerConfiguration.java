package github.caicosantos.concierge.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Value("${app.security.key-store-path}")
    private String keyStorePath;
    @Value("${app.security.key-store-password}")
    private String keyStorePassword;
    @Value("${app.security.key-alias}")
    private String keyAlias;
    @Value("${app.security.key-password}")
    private String keyPassword;

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity hs) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(hs);
        hs.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        hs.oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()));
        return hs.build();
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings
                .builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .build();
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings
                .builder()
                .requireAuthorizationConsent(false)
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        try {
            RSAKey rsaKey = loadRsaKey();
            JWKSet jwkSet = new JWKSet(rsaKey);
            return new ImmutableJWKSet<>(jwkSet);
        } catch(Exception e) {
            throw new BeanCreationException("Failure the configurer the JWKSource!");
        }
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return  context -> {
            if(OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                Authentication principal = context.getPrincipal();

                Set<String> authorities = principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims().claim("authorities", authorities);
            }
        };
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    private RSAKey loadRsaKey() throws Exception {
        String cleanedPath = keyStorePath.replace("classpath:","");
        ClassPathResource resource = new ClassPathResource(cleanedPath);
        if(!resource.exists()) {
            throw new IllegalArgumentException("The file keystore not was found");
        }
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try(InputStream is = resource.getInputStream()) {
            keyStore.load(is, keyStorePassword.toCharArray());
        }
        Key key = keyStore.getKey(keyAlias, keyPassword.toCharArray());
        Certificate certificate = keyStore.getCertificate(keyAlias);
        if(key==null) {
            throw new IllegalStateException("No key was found for an Alias!");
        }
        if(certificate==null) {
            throw new IllegalStateException("No certificate was found for an Alias!");
        }
        if(key instanceof RSAPrivateKey && certificate.getPublicKey() instanceof RSAPublicKey) {
            return new RSAKey
                    .Builder((RSAPublicKey) certificate.getPublicKey())
                    .privateKey((RSAPrivateKey) key)
                    .keyID(keyAlias)
                    .build();
        }
        throw new RuntimeException("The key pair found in Alias is not of the RSA type!");
    }
}
