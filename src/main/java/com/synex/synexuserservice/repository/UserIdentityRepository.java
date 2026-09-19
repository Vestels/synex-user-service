package com.synex.synexuserservice.repository;

import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.enums.IdentityProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserIdentityRepository extends JpaRepository<UserIdentityEntity, Long> {
    Optional<UserIdentityEntity> findByProviderAndSubject(IdentityProvider provider, String subject);
    Optional<UserIdentityEntity> findByUserId(UUID userId);
    Optional<List<UserIdentityEntity>> findAllByUserId(UUID userId);
    Optional<UserIdentityEntity> findByUserIdAndProviderAndSubject(
            UUID userId,
            IdentityProvider provider,
            String subject
    );
    void deleteAllByUserId(UUID userId);
}
