package com.synex.synexuserservice.dto.request;

import com.synex.synexuserservice.entity.UserPreferencesEntity;
import com.synex.synexuserservice.enums.Language;
import com.synex.synexuserservice.enums.Theme;
import com.synex.synexuserservice.enums.UnitSystem;

public record UpdateUserPreferencesRequestDto(
        Language language,
        UnitSystem unitSystem,
        Theme theme,
        Boolean emailNotifications,
        Boolean pushNotifications
) {

    public boolean isEmpty() {
        return language == null &&
                unitSystem == null &&
                theme == null &&
                emailNotifications == null &&
                pushNotifications == null;
    }

    public UserPreferencesEntity updateEntity(UserPreferencesEntity entity) {
        if (language != null) {
            entity.setLanguage(language);
        }
        if (unitSystem != null) {
            entity.setUnitSystem(unitSystem);
        }

        if (theme != null) {
            entity.setTheme(theme);
        }

        if (emailNotifications != null) {
            entity.setEmailNotifications(emailNotifications);
        }

        if (pushNotifications != null) {
            entity.setPushNotifications(pushNotifications);
        }

        return entity;
    }
}
