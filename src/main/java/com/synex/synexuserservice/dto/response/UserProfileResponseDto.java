package com.synex.synexuserservice.dto.response;

import com.synex.synexuserservice.entity.UserProfileEntity;
import com.synex.synexuserservice.enums.Gender;

import java.time.LocalDate;

public record UserProfileResponseDto(
        LocalDate birthDate,
        String nickname,
        String firstName,
        String lastName,
        Gender gender
) {

    public static UserProfileResponseDto from(UserProfileEntity userProfile) {
        return new UserProfileResponseDto(
                userProfile.getBirthDate(),
                userProfile.getNickname(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getGender()
        );
    }
}
