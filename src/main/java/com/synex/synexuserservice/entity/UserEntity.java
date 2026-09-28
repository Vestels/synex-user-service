package com.synex.synexuserservice.entity;

import com.synex.synexuserservice.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @Email(flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private UserStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    private Instant lastLoginAt;

    private Instant lastActivityAt;

    private Instant deletionRequestAt;

    private Instant scheduledDeletionAt;

    public UserEntity(String email) {
        this.publicId = UUID.randomUUID();
        this.status = UserStatus.ACTIVE;
        this.email = email;
    }

    public void updateLastLogin() {
        this.lastLoginAt = Instant.now();
    }

    public void updateLastActivityAt() {
        Instant now = Instant.now();

        if (lastActivityAt == null || lastActivityAt.plus(5, ChronoUnit.MINUTES).isBefore(now)) {
            this.lastActivityAt = now;
        }
    }

    public void startDeletion() {
        this.status = UserStatus.DELETING;
    }

    public void setScheduledDeletion() {
        this.status = UserStatus.PENDING_DELETION;
        this.deletionRequestAt = Instant.now();

//        TODO - change to plus 30 days later
        this.scheduledDeletionAt = this.deletionRequestAt.plus(5, ChronoUnit.MINUTES);
    }

    public void clearScheduledDeletion() {
        this.status = UserStatus.ACTIVE;
        this.deletionRequestAt = null;
        this.scheduledDeletionAt = null;
    }
}
