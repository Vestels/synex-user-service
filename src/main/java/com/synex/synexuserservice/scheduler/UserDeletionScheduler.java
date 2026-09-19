package com.synex.synexuserservice.scheduler;

import com.synex.synexuserservice.service.support.DeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeletionScheduler {

    private final DeletionService deletionService;

    //    TODO - change to 1 hour later
    @Scheduled(fixedRate = 60_000)
    public void deleteExpiredUsers() {
        deletionService.deleteExpiredUsers();
    }
}