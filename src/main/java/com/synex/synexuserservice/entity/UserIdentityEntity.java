package com.synex.synexuserservice.entity;

import com.synex.synexuserservice.enums.IdentityProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Table(
        name = "user_identity",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_identity_provider_subject",
                        columnNames = {"provider", "subject"}
                )
        }
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserIdentityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private IdentityProvider provider;

    @Column(nullable = false, updatable = false)
    private String subject;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant lastUsedAt;

    public UserIdentityEntity(UUID userId, IdentityProvider provider, String subject) {
        this.userId = userId;
        this.provider = provider;
        this.subject = subject;
        this.lastUsedAt = Instant.now();
    }

    public void updateLastUsedAt() {
        this.lastUsedAt = Instant.now();
    }
}
