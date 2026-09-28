package com.synex.synexuserservice.service;

import com.synex.synexuserservice.dto.response.UserIdentityResponseDto;
import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.enums.IdentityProvider;
import com.synex.synexuserservice.repository.UserIdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserIdentityService {

    private final UserIdentityRepository userIdentityRepository;

    @Transactional(readOnly = true)
    public Optional<UserIdentityEntity> getAuthenticatedUserIdentityProvider(IdentityProvider provider, String subject) {
        return userIdentityRepository.findByProviderAndSubject(provider, subject);
    }

    @Transactional(readOnly = true)
    public List<UserIdentityResponseDto> getAllUserIdentities(UUID publicID) {
        return userIdentityRepository.findAllByUserId(publicID)
                .stream()
                .map(UserIdentityResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserIdentityEntity> findByUserId(UUID userId) {
        return userIdentityRepository.findByUserId(userId);
    }

    @Transactional
    public void saveUserIdentity(UserIdentityEntity identity) {
        userIdentityRepository.save(identity);
    }
}
