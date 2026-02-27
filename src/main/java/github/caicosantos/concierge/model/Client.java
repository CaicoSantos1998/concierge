package github.caicosantos.concierge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "client")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String clientId;
    private String clientSecret;
    @Column(name = "redirect-uri")
    private String redirectURI;
    private String scope;
}
