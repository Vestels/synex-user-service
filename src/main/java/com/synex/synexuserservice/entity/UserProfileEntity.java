package com.synex.synexuserservice.entity;

import com.synex.synexuserservice.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "user_profile")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private UUID userId;

    @Setter
    private String firstName;

    @Setter
    private String lastName;

    @Setter
    private String nickname;

    @Setter
    private LocalDate birthDate;

    @Enumerated(value = EnumType.STRING)
    @Setter
    private Gender gender;

    public UserProfileEntity(UUID userId) {
        this.userId = userId;
    }
}
