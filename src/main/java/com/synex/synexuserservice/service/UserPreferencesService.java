package com.synex.synexuserservice.service;

import com.synex.synexuserservice.dto.request.UpdateUserPreferencesRequestDto;
import com.synex.synexuserservice.dto.response.UserPreferencesAppBehaviourDto;
import com.synex.synexuserservice.dto.response.UserPreferencesResponseDto;
import com.synex.synexuserservice.entity.UserPreferencesEntity;
import com.synex.synexuserservice.exception.UserPreferencesNotFoundException;
import com.synex.synexuserservice.repository.UserPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPreferencesService {

    private final UserPreferencesRepository userPreferencesRepository;

    @Transactional(readOnly = true)
    public UserPreferencesResponseDto getUserPreferences(UUID userId) {
        return UserPreferencesResponseDto.from(userPreferencesRepository.findByUserId(userId)
                .orElseThrow(() -> new UserPreferencesNotFoundException("User Preferences not found."))
        );
    }

    @Transactional(readOnly = true)
    public UserPreferencesAppBehaviourDto getUserAppBehaviourPreferences(UUID userId) {
        return UserPreferencesAppBehaviourDto.from(userPreferencesRepository.findByUserId(userId)
                .orElseThrow(() -> new UserPreferencesNotFoundException("User Preferences not found."))
        );
    }

    @Transactional
    public void saveUserPreferences(UserPreferencesEntity userPreferences) {
        userPreferencesRepository.save(userPreferences);
    }

    @Transactional
    public void updateUserPreferences(UpdateUserPreferencesRequestDto userPreference, UUID userId) {
        this.saveUserPreferences(userPreference.updateEntity(userPreferencesRepository.findByUserId(userId)
                .orElseThrow(() -> new UserPreferencesNotFoundException("User Preferences not found.")))
        );
    }
}
