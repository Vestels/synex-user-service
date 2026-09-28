package com.synex.synexuserservice.service.support;

import com.synex.synexuserservice.entity.UserEntity;
import com.synex.synexuserservice.entity.UserIdentityEntity;
import com.synex.synexuserservice.enums.UserStatus;
import com.synex.synexuserservice.exception.UserIdentityNotFoundException;
import com.synex.synexuserservice.service.UserIdentityService;
import com.synex.synexuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeletionService {

    private final UserService userService;
    private final UserIdentityService userIdentityService;
    private final Auth0ManagementService auth0ManagementService;
    private final OwnUserDeletionService ownUserDeletionService;

    public void deleteExpiredUsers() {
        Instant now = Instant.now();

        List<UserEntity> users = userService.findAllByStatusInAndScheduledDeletionAtBefore(
                List.of(
                        UserStatus.PENDING_DELETION,
                        UserStatus.DELETING
                ),
                now);

        for (UserEntity user : users) {
            try {
                processDeletion(user);
            } catch (Exception exception) {
//                TODO - logging
            }
        }
    }

    private void processDeletion(UserEntity user) {
        UUID publicId = user.getPublicId();

        user.startDeletion();
        userService.saveUser(user);

        UserIdentityEntity identity = userIdentityService.findByUserId(publicId)
                .orElseThrow(() -> new UserIdentityNotFoundException("User identity not found."));

        auth0ManagementService.deleteUser(identity.getSubject());

        ownUserDeletionService.deleteUser(publicId);
    }
}
