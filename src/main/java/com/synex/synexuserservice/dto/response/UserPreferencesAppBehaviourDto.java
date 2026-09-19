package com.synex.synexuserservice.dto.response;

import com.synex.synexuserservice.entity.UserPreferencesEntity;
import com.synex.synexuserservice.enums.Language;
import com.synex.synexuserservice.enums.Theme;

public record UserPreferencesAppBehaviourDto(
        Language language,
        Theme theme
) {

    public static UserPreferencesAppBehaviourDto from(UserPreferencesEntity preferences) {
        return new UserPreferencesAppBehaviourDto(
                preferences.getLanguage(),
                preferences.getTheme()
        );
    }
}
