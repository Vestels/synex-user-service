package com.synex.synexuserservice.service.support;

import com.synex.synexuserservice.repository.UserIdentityRepository;
import com.synex.synexuserservice.repository.UserPreferencesRepository;
import com.synex.synexuserservice.repository.UserProfileRepository;
import com.synex.synexuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnUserDeletionService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final UserIdentityRepository userIdentityRepository;

    @Transactional
    public void deleteUser(UUID publicId) {
        userProfileRepository.deleteByUserId(publicId);
        userPreferencesRepository.deleteByUserId(publicId);
        userIdentityRepository.deleteByUserId(publicId);
        userRepository.deleteByPublicId(publicId);
    }
}
