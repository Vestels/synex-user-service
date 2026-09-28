package com.synex.synexuserservice.entity;

import com.synex.synexuserservice.enums.Language;
import com.synex.synexuserservice.enums.Theme;
import com.synex.synexuserservice.enums.UnitSystem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "user_preferences")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserPreferencesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private UUID userId;

    @Enumerated(value = EnumType.STRING)
    @Setter
    private Language language;

    @Enumerated(value = EnumType.STRING)
    @Setter
    private UnitSystem unitSystem;

    @Setter
    private boolean emailNotifications;

    @Setter
    private boolean pushNotifications;

    @Enumerated(value = EnumType.STRING)
    @Setter
    private Theme theme;

    public UserPreferencesEntity(UUID userId) {
        this.userId = userId;
        this.language = Language.HU;
        this.unitSystem = UnitSystem.METRIC;
        this.theme = Theme.LIGHT;
        this.emailNotifications = false;
        this.pushNotifications = false;
    }
}
