package com.synex.synexuserservice.service;

import com.synex.synexuserservice.dto.request.UpdateUserProfileRequestDto;
import com.synex.synexuserservice.dto.response.UserProfileResponseDto;
import com.synex.synexuserservice.entity.UserProfileEntity;
import com.synex.synexuserservice.exception.UserProfileNotFoundException;
import com.synex.synexuserservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional(readOnly = true)
    public UserProfileResponseDto getUserProfile(UUID userId) {
        return UserProfileResponseDto.from(userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserProfileNotFoundException("User profile not found."))
        );
    }

    @Transactional
    public void saveUserProfile(UserProfileEntity userProfile) {
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void updateUserprofile(UpdateUserProfileRequestDto user, UUID userId) {
        this.saveUserProfile(user.updateEntity(userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new UserProfileNotFoundException("User profile not found.")))
        );
    }
}
