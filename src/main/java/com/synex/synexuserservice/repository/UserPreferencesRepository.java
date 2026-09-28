package com.synex.synexuserservice.repository;

import com.synex.synexuserservice.entity.UserPreferencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferencesEntity, Long> {
    Optional<UserPreferencesEntity> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
