package com.synex.synexuserservice.dto.response;

import com.synex.synexuserservice.entity.UserPreferencesEntity;
import com.synex.synexuserservice.enums.Language;
import com.synex.synexuserservice.enums.Theme;
import com.synex.synexuserservice.enums.UnitSystem;

public record UserPreferencesResponseDto(
        Language language,
        UnitSystem unitSystem,
        Theme theme,
        boolean emailNotifications,
        boolean pushNotifications
) {

    public static UserPreferencesResponseDto from(UserPreferencesEntity userPreferences) {
        return new UserPreferencesResponseDto(
                userPreferences.getLanguage(),
                userPreferences.getUnitSystem(),
                userPreferences.getTheme(),
                userPreferences.isEmailNotifications(),
                userPreferences.isPushNotifications()
        );
    }
}
